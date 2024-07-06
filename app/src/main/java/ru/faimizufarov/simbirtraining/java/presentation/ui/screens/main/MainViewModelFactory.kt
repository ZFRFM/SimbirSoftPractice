package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.faimizufarov.simbirtraining.java.data.repository.NewsRepositoryImpl
import ru.faimizufarov.simbirtraining.java.domain.repository.NewsRepository
import javax.inject.Inject

class MainViewModelFactory
    @Inject
    constructor(
        val newsRepository: NewsRepository,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MainViewModel(
                newsRepository as NewsRepositoryImpl,
            ) as T
    }
