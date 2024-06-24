package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.launch
import ru.faimizufarov.simbirtraining.java.data.models.News
import ru.faimizufarov.simbirtraining.java.data.repositories.NewsRepository
import ru.faimizufarov.simbirtraining.java.presentation.ui.holders.BadgeCounterHolder
import ru.faimizufarov.simbirtraining.java.presentation.ui.holders.NewsListHolder

class NewsViewModel(
    context: Context,
) : ViewModel() {
    private val _news = MutableLiveData<List<News>>()
    val news: LiveData<List<News>> = _news

    private val _newsRepository = MutableLiveData(NewsRepository(context))
    val newsRepository: LiveData<NewsRepository>
        get() = _newsRepository

    private fun setNews(news: List<News>) {
        _news.value = news
    }

    suspend fun loadServerNews(ids: List<String>): List<News>? {
        val serverNews = newsRepository.value?.getNewsList(ids)
        viewModelScope.launch {
            BadgeCounterHolder.setBadgeCounterEmitValue(serverNews?.size ?: 0)
            NewsListHolder.setNewsList(serverNews ?: emptyList())
            setNews(serverNews ?: emptyList())
        }
        return serverNews
    }

    companion object {
        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    val application = checkNotNull(extras[APPLICATION_KEY])

                    return NewsViewModel(
                        application.baseContext.applicationContext,
                    ) as T
                }
            }
    }
}
