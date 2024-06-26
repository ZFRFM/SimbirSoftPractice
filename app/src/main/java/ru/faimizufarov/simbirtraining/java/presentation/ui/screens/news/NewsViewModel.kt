package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news

import android.content.Context
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.faimizufarov.simbirtraining.java.App
import ru.faimizufarov.simbirtraining.java.data.models.CategoryFilter
import ru.faimizufarov.simbirtraining.java.data.models.News
import ru.faimizufarov.simbirtraining.java.data.repositories.NewsRepository
import ru.faimizufarov.simbirtraining.java.presentation.ui.holders.BadgeCounterHolder
import ru.faimizufarov.simbirtraining.java.presentation.ui.holders.GlobalNewsFilterHolder
import ru.faimizufarov.simbirtraining.java.presentation.ui.holders.NewsFilterHolder

class NewsViewModel(
    private val newsRepository: NewsRepository,
) : ViewModel() {
    // TODO: to newsRepository. Holders are for babies and shitcoders, prove you're neither
    private val newsFilterHolder: NewsFilterHolder = GlobalNewsFilterHolder

    val newsLiveData: LiveData<List<News>> =
        combine(
            newsRepository.newsListFlow,
            newsFilterHolder.activeFiltersFlow,
        ) { news, filters ->
            news.filter { article ->
                val isFilteredIn = filters.any { filter -> filter.categoryId in article.categoryIds }
                filters.isEmpty() || isFilteredIn
            }
        }
            .onEach { news ->
                BadgeCounterHolder.setBadgeCounterEmitValue(news.size)
            }
            .asLiveData(Dispatchers.IO)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            newsFilterHolder.activeFiltersFlow
                .map { filters -> filters.map(CategoryFilter::categoryId) }
                .collect { ids ->
                    newsRepository.requestNewsList(ids)
                }
        }
    }

    class Factory(context: Context) : ViewModelProvider.Factory {
        private val newsRepository = (context.applicationContext as App).newsRepository

        override fun <T : ViewModel> create(modelClass: Class<T>): T = NewsViewModel(newsRepository) as T
    }
}
