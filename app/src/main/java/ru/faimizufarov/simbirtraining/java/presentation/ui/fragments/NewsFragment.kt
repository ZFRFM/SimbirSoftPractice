package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.FragmentNewsBinding
import ru.faimizufarov.simbirtraining.java.data.models.CategoryFilter
import ru.faimizufarov.simbirtraining.java.data.models.News
import ru.faimizufarov.simbirtraining.java.presentation.ui.adapters.NewsAdapter
import java.util.concurrent.Executors

class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding

    private val newsFilterHolder: NewsFilterHolder = GlobalNewsFilterHolder

    private val newsAdapter = NewsAdapter(onItemClick = ::updateFeed)
    private var appliedFiltersNews = mutableListOf<News>()

    private val readNewsIdsStateFlow: MutableStateFlow<List<Int>> =
        MutableStateFlow(listOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            getFromSavedInstanceState(savedInstanceState)
        } else {
            loadListOfNews()
        }

        lifecycleScope.launch {
            readNewsIdsStateFlow.collect {
                val availableNews =
                    appliedFiltersNews
                        .takeIf(List<News>::isNotEmpty)
                        ?: NewsListHolder.getNewsList()

                val unreadNews =
                    availableNews.filter { news: News ->
                        !readNewsIdsStateFlow.value.contains(news.id)
                    }

                BadgeCounter.setBadgeCounterEmitValue(unreadNews.size)
            }
        }

        newsFilterHolder.setOnFilterChangedListener { listFilters ->
            lifecycleScope.launch {
                val localFiltersList = newsFilterHolder.filters
                localFiltersList.forEach { filteredCategory ->
                    if (listFilters.contains(filteredCategory)) {
                        val filteredNews =
                            NewsListHolder.getNewsList().filterByCategory(filteredCategory)
                        appliedFiltersNews.addAll(filteredNews)
                    }
                }
                val badgeUpdatedCount =
                    appliedFiltersNews.toSet().filter { news: News ->
                        !readNewsIdsStateFlow.value.contains(news.id)
                    }.size
                BadgeCounter.setBadgeCounterEmitValue(badgeUpdatedCount)
                updateAdapter(appliedFiltersNews)
            }
        }

        updateAdapter(NewsListHolder.getNewsList())

        binding.imageViewFilter.setOnClickListener {
            openFilterFragment()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        putInSavedInstanceState(outState)
    }

    private fun getFromSavedInstanceState(savedInstanceState: Bundle) {
        val newsArray =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                savedInstanceState.getParcelable(LIST_OF_NEWS, ArrayList::class.java)
            } else {
                savedInstanceState.getParcelable(LIST_OF_NEWS)
            } ?: return
        NewsListHolder.setNewsList(newsArray.filterIsInstance<News>())

        newsAdapter.submitList(NewsListHolder.getNewsList())
    }

    private fun putInSavedInstanceState(outState: Bundle) {
        val arrayList = ArrayList<News>()
        NewsListHolder.getNewsList().forEach {
            arrayList.add(it)
        }
        outState.putParcelableArrayList(LIST_OF_NEWS, arrayList)
    }

    private fun loadListOfNews() {
        val executor = Executors.newSingleThreadExecutor()
        val fragmentContext = requireContext()

        val jsonFlow =
            flow {
                val newsJsonInString = NewsListHolder.getNewsJson(fragmentContext)
                delay(5000)
                emit(newsJsonInString)
            }.flowOn(Dispatchers.IO)

        executor.execute {
            lifecycleScope.launch {
                jsonFlow.collect { json ->
                    NewsListHolder.setNewsList(NewsListHolder.getNewsListFromJson(json))
                    val localNewsListKeeper = NewsListHolder.getNewsList()
                    BadgeCounter.setBadgeCounterEmitValue(localNewsListKeeper.size)
                    newsAdapter.submitList(localNewsListKeeper)
                }
            }
            executor.shutdown()
        }
    }

    private fun List<News>.filterByCategory(categoryFilter: CategoryFilter) =
        filter { news ->
            news.helpCategory.any { it == categoryFilter.enumValue } && categoryFilter.checked
        }

    private fun openFilterFragment() {
        parentFragmentManager.beginTransaction().add(
            R.id.fragmentContainerView,
            NewsFilterFragment.newInstance(),
        ).commit()
    }

    private fun updateFeed(news: News) {
        lifecycleScope.launch {
            readNewsIdsStateFlow.emit(readNewsIdsStateFlow.value + news.id)
        }

        val startDate = news.startDate.toString()
        val finishDate = news.finishDate.toString()

        val bundle =
            bundleOf(
                DetailDescriptionFragment.IMAGE_VIEW_NEWS
                        to news.newsImageUrl,
                DetailDescriptionFragment.TEXT_VIEW_NAME
                        to news.nameText,
                DetailDescriptionFragment.TEXT_VIEW_DESCRIPTION
                        to news.descriptionText,
                DetailDescriptionFragment.TEXT_VIEW_REMAINING_TIME
                        to news.remainingTimeText,
                DetailDescriptionFragment.START_DATE
                        to startDate,
                DetailDescriptionFragment.FINISH_DATE
                        to finishDate,
            )

        setFragmentResult(DetailDescriptionFragment.NEWS_POSITION_RESULT, bundle)
        parentFragmentManager.beginTransaction().add(
            R.id.fragmentContainerView,
            DetailDescriptionFragment.newInstance(),
        ).commit()
    }

    private fun updateAdapter(list: List<News>) {
        newsAdapter.submitList(list.toSet().toList())
        binding.contentNews.recyclerViewNewsFragment.adapter = newsAdapter
        appliedFiltersNews = mutableListOf()
    }

    companion object {
        fun newInstance() = NewsFragment()

        private const val LIST_OF_NEWS = "LIST_OF_NEWS"
    }
}
