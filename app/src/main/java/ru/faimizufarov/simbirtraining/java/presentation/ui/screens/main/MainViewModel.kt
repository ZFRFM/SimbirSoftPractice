package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import ru.faimizufarov.simbirtraining.java.App
import ru.faimizufarov.simbirtraining.java.data.repository.NewsRepositoryImpl

class MainViewModel(
    newsRepositoryImpl: NewsRepositoryImpl,
) : ViewModel() {
    val badgeCountLiveData: LiveData<Int> =
        newsRepositoryImpl.badgeCounterFlow.asLiveData(Dispatchers.IO)

    class Factory(context: Context) : ViewModelProvider.Factory {
        private val newsRepository = (context.applicationContext as App).newsRepositoryImpl

        override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(newsRepository) as T
    }
}
