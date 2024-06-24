package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import ru.faimizufarov.simbirtraining.java.data.models.News
import ru.faimizufarov.simbirtraining.java.data.repositories.NewsRepository

class NewsViewModel(
    context: Context,
) : ViewModel() {
    private val _news = MutableLiveData<List<News>>()
    val news: LiveData<List<News>>
        get() = _news

    private val _newsRepository = MutableLiveData(NewsRepository(context))
    val newsRepository: LiveData<NewsRepository>
        get() = _newsRepository

    fun setNews(news: List<News>) {
        _news.value = news
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
