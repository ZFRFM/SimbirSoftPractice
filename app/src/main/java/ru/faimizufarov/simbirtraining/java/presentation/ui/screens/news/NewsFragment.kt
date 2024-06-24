package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.FragmentNewsBinding
import ru.faimizufarov.simbirtraining.java.data.models.CategoryFilter
import ru.faimizufarov.simbirtraining.java.data.models.News
import ru.faimizufarov.simbirtraining.java.presentation.ui.holders.BadgeCounterHolder
import ru.faimizufarov.simbirtraining.java.presentation.ui.holders.GlobalNewsFilterHolder
import ru.faimizufarov.simbirtraining.java.presentation.ui.holders.NewsFilterHolder
import ru.faimizufarov.simbirtraining.java.presentation.ui.holders.NewsListHolder
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.detail_description.DetailDescriptionFragment
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news.adapters.NewsAdapter
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news_filter.NewsFilterFragment

class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding

    private val newsFilterHolder: NewsFilterHolder = GlobalNewsFilterHolder

    private val newsAdapter = NewsAdapter(onItemClick = ::updateFeed)
    private val appliedFiltersNews = mutableListOf<News>()

    private val newsViewModel: NewsViewModel by viewModels { NewsViewModel.Factory }

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

        loadServerNews(emptyList())
        newsViewModel.news.observe(viewLifecycleOwner) { news ->
            NewsListHolder.setNewsList(news)
            newsAdapter.submitList(NewsListHolder.getNewsList())
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

                BadgeCounterHolder.setBadgeCounterEmitValue(unreadNews.size)
            }
        }

        lifecycleScope.launch {
            newsFilterHolder.activeFiltersFlow.collect { activeFilters ->
                val coroutineExceptionHandler =
                    CoroutineExceptionHandler { _, _: Throwable ->
                        launch {
                            loadFilteredLocalNews(activeFilters)
                        }
                    }

                launch(coroutineExceptionHandler) {
                    loadServerNews(activeFilters.map { it.categoryId })
                }
            }
        }

        updateAdapter(NewsListHolder.getNewsList())

        binding.imageViewFilter.setOnClickListener {
            openFilterFragment()
        }
    }

    private fun loadServerNews(ids: List<String>) {
        lifecycleScope.launch {
            val serverNews =
                withContext(Dispatchers.IO) {
                    newsViewModel.newsRepository.value?.getNewsList(ids)
                }

            BadgeCounterHolder.setBadgeCounterEmitValue(serverNews?.size ?: 0)
            NewsListHolder.setNewsList(serverNews ?: emptyList())
            newsViewModel.setNews(serverNews ?: emptyList())

            newsAdapter.submitList(serverNews)
        }
    }

    private suspend fun loadFilteredLocalNews(filters: List<CategoryFilter>) {
        val filteredNews = NewsListHolder.getNewsList().applyCategoryFilters(filters)
        appliedFiltersNews.addAll(filteredNews)
        val badgeUpdatedCount =
            appliedFiltersNews.toSet().filter { news: News ->
                !readNewsIdsStateFlow.value.contains(news.id)
            }.size
        BadgeCounterHolder.setBadgeCounterEmitValue(badgeUpdatedCount)
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
    }
}
