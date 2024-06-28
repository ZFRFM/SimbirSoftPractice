package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import ru.faimizufarov.simbirtraining.java.App
import ru.faimizufarov.simbirtraining.java.data.repositories.NewsRepository

class MainViewModel(
    newsRepository: NewsRepository,
) : ViewModel() {
    val badgeCountLiveData: LiveData<Int> =
        newsRepository.badgeCounterFlow.asLiveData(Dispatchers.IO)

    class Factory(context: Context) : ViewModelProvider.Factory {
        private val newsRepository = (context.applicationContext as App).newsRepository

        override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(newsRepository) as T
    }
}
