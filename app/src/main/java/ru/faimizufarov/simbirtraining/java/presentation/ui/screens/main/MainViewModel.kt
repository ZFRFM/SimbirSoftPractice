package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import ru.faimizufarov.data.repository.NewsRepositoryImpl

class MainViewModel(
    newsRepositoryImpl: NewsRepositoryImpl,
) : ViewModel() {
    val badgeCountLiveData: LiveData<Int> =
        newsRepositoryImpl.badgeCounterFlow.asLiveData(Dispatchers.IO)
}
