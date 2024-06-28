@file:Suppress("ktlint:standard:no-wildcard-imports")

package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news

import android.content.Context
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.faimizufarov.simbirtraining.java.App
import ru.faimizufarov.simbirtraining.java.data.models.CategoryFilter
import ru.faimizufarov.simbirtraining.java.data.models.News
import ru.faimizufarov.simbirtraining.java.data.repositories.CategoryRepository
import ru.faimizufarov.simbirtraining.java.data.repositories.NewsRepository
import ru.faimizufarov.simbirtraining.java.presentation.ui.holders.NewsFilter

class NewsViewModel(
    private val newsRepository: NewsRepository,
    categoryRepository: CategoryRepository,
) : ViewModel() {
    private val newsFilters: NewsFilter = categoryRepository.newsFilters

    val newsLiveData: LiveData<List<News>> =
        combine(
            newsRepository.newsListFlow,
            newsFilters.activeFiltersFlow,
        ) { news, filters ->
            news.filter { article ->
                val isFilteredIn =
                    filters.any { filter -> filter.categoryId in article.categoryIds }
                filters.isEmpty() || isFilteredIn
            }
        }
            .onEach { news ->
                newsRepository.setBadgeCounterEmitValue(news.size)
            }
            .asLiveData(Dispatchers.IO)

    private val readNewsIdsStateFlow: MutableStateFlow<List<String>> =
        MutableStateFlow(listOf())

    fun emitReadNewsIds(news: News) {
        viewModelScope.launch {
            readNewsIdsStateFlow.emit(readNewsIdsStateFlow.value + news.id)
        }
    }

    fun collectReadNewsIds() {
        viewModelScope.launch {
            readNewsIdsStateFlow.collect {
                val availableNews = newsLiveData.value

                val unreadNews =
                    availableNews?.filter { news: News ->
                        !readNewsIdsStateFlow.value.contains(news.id)
                    } ?: emptyList()

                newsRepository.setBadgeCounterEmitValue(unreadNews.size)
            }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            newsFilters.activeFiltersFlow
                .map { filters -> filters.map(CategoryFilter::categoryId) }
                .collect { ids ->
                    newsRepository.requestNewsList(ids)
                }
        }
    }

    class Factory(context: Context) : ViewModelProvider.Factory {
        private val newsRepository = (context.applicationContext as App).newsRepository
        private val categoryRepository = (context.applicationContext as App).categoriesRepository

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            NewsViewModel(
                newsRepository,
                categoryRepository,
            ) as T
    }
}
