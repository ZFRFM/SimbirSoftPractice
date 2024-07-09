@file:Suppress("ktlint:standard:no-wildcard-imports")

package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.faimizufarov.simbirtraining.java.data.models.CategoryFilter
import ru.faimizufarov.simbirtraining.java.data.repository.NewsRepositoryImpl
import ru.faimizufarov.simbirtraining.java.domain.models.News
import ru.faimizufarov.simbirtraining.java.domain.usecase.GetNewsUseCase
import ru.faimizufarov.simbirtraining.java.domain.usecase.SetBadgeCounterEmitValueUseCase
import ru.faimizufarov.simbirtraining.java.presentation.ui.holders.GlobalNewsFilter

class NewsViewModel(
    private val getNewsUseCase: GetNewsUseCase,
    private val setBadgeCounterEmitValueUseCase: SetBadgeCounterEmitValueUseCase,
    private val newsFilters: GlobalNewsFilter,
    newsRepositoryImpl: NewsRepositoryImpl,
) : ViewModel() {
    val newsLiveData: LiveData<List<News>> =
        combine(
            newsRepositoryImpl.newsListFlow,
            newsFilters.activeFiltersFlow,
        ) { news, filters ->
            news.filterByCategoryId(filters)
        }
            .onEach { news ->
                setBadgeCounterEmitValueUseCase.execute(news.size)
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

                setBadgeCounterEmitValueUseCase.execute(unreadNews.size)
            }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            newsFilters.activeFiltersFlow
                .map { filters -> filters.map(CategoryFilter::categoryId) }
                .collect { ids ->
                    getNewsUseCase.execute(ids)
                }
        }
    }

    private fun List<News>.filterByCategoryId(filters: List<CategoryFilter>) =
        this.filter { article ->
            val isFilteredIn =
                filters.any { filter -> filter.categoryId in article.categoryIds }
            filters.isEmpty() || isFilteredIn
        }
}
