package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.faimizufarov.simbirtraining.java.data.repository.NewsRepositoryImpl
import ru.faimizufarov.simbirtraining.java.domain.models.News

class SearchViewPagerEventsViewModel(
    private val newsRepositoryImpl: NewsRepositoryImpl,
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
            newsRepositoryImpl.newsListFlow.collect { collectedNewsList ->
                _newsList.value = collectedNewsList
            }
        }
    }
}
