package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.search

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.faimizufarov.simbirtraining.java.App
import ru.faimizufarov.simbirtraining.java.data.models.News
import ru.faimizufarov.simbirtraining.java.data.repositories.NewsRepository

class SearchViewPagerEventsViewModel(
    private val newsRepository: NewsRepository,
) : ViewModel() {
    private val _newsList = MutableLiveData<List<News>>()
    val newsList: LiveData<List<News>> = _newsList

    private val _isNewsAvailable = MutableLiveData<Boolean>()
    val isNewsAvailable: LiveData<Boolean> = _isNewsAvailable

    fun clickOnEventPosition() {
        val errorHandler =
            CoroutineExceptionHandler { _, error: Throwable ->
                _isNewsAvailable.value = false
            }

        viewModelScope.launch(errorHandler) {
            delay(1000)
            throw Exception("Error on server side")
        }
    }

    init {
        viewModelScope.launch {
            newsRepository.newsListFlow.collect { collectedNewsList ->
                _newsList.value = collectedNewsList
            }
        }
    }

    class Factory(context: Context) : ViewModelProvider.Factory {
        private val newsRepository = (context.applicationContext as App).newsRepository

        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            SearchViewPagerEventsViewModel(
                newsRepository,
            ) as T
    }
}
