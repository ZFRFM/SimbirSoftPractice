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
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.FragmentNewsBinding
import ru.faimizufarov.simbirtraining.java.data.models.CategoryFilter
import ru.faimizufarov.simbirtraining.java.data.models.News
import ru.faimizufarov.simbirtraining.java.data.models.mapToNews
import ru.faimizufarov.simbirtraining.java.network.AppApi
import ru.faimizufarov.simbirtraining.java.presentation.ui.adapters.NewsAdapter
import java.util.concurrent.Executors

class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding

    private val newsFilterHolder: NewsFilterHolder = GlobalNewsFilterHolder

    private val disposables = CompositeDisposable()

    private val newsAdapter = NewsAdapter(onItemClick = ::updateFeed)
    private val appliedFiltersNews = mutableListOf<News>()

    private val readNewsIdsStateFlow: MutableStateFlow<List<String>> =
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
            lifecycleScope.launch {
                loadServerNews(emptyList())
            }
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

        lifecycleScope.launch {
            newsFilterHolder.activeFiltersFlow.collect { activeFilters ->
                val coroutineExceptionHandler =
                    CoroutineExceptionHandler { _, _: Throwable ->
                        lifecycleScope.launch {
                            loadFilteredLocalNews(activeFilters)
                        }
                    }

                lifecycleScope.launch(coroutineExceptionHandler) {
                    loadServerNews(activeFilters.map { it.categoryId })
                }
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

    private suspend fun loadServerNews(ids: List<String>) {
        val newsResponses = AppApi.retrofitService.getEvents(ids)
        val serverNews = newsResponses.map { it.mapToNews() }
        lifecycleScope.launch {
            BadgeCounter.setBadgeCounterEmitValue(serverNews.size)
        }
        NewsListHolder.setNewsList(serverNews)
        newsAdapter.submitList(serverNews)
    }

    private fun loadAssetsNews() {
        val executor = Executors.newSingleThreadExecutor()
        val fragmentContext = requireContext()

        val jsonFlow =
            flow {
                val newsJsonInString = NewsListHolder.getNewsJson(fragmentContext)
                delay(1000)
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

    private suspend fun loadFilteredLocalNews(filters: List<CategoryFilter>) {
        val filteredNews = NewsListHolder.getNewsList().applyCategoryFilters(filters)
        appliedFiltersNews.addAll(filteredNews)
        val badgeUpdatedCount =
            appliedFiltersNews.toSet().filter { news: News ->
                !readNewsIdsStateFlow.value.contains(news.id)
            }.size
        BadgeCounter.setBadgeCounterEmitValue(badgeUpdatedCount)
        updateAdapter(appliedFiltersNews)
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

        val startDate =
            Instant.fromEpochMilliseconds(news.startDate)
                .toLocalDateTime(TimeZone.currentSystemDefault()).toString()
        val finishDate =
            Instant.fromEpochMilliseconds(news.finishDate)
                .toLocalDateTime(TimeZone.currentSystemDefault()).toString()

        val bundle =
            bundleOf(
                DetailDescriptionFragment.IMAGES_VIEW_NEWS
                    to news.newsImages,
                DetailDescriptionFragment.TEXT_VIEW_NAME
                    to news.nameText,
                DetailDescriptionFragment.TEXT_VIEW_DESCRIPTION
                    to news.descriptionText,
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
        appliedFiltersNews.clear()
    }

    private fun List<News>.applyCategoryFilters(filters: List<CategoryFilter>) =
        filter { article ->
            val isFilteredIn by lazy {
                filters.any { filter -> filter.categoryId in article.categoryIds }
            }
            filters.isEmpty() || isFilteredIn
        }

    companion object {
        fun newInstance() = NewsFragment()

        private const val LIST_OF_NEWS = "LIST_OF_NEWS"
    }
}
