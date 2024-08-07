package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.faimizufarov.data.repository.NewsRepositoryImpl
import ru.faimizufarov.domain.repository.NewsRepository
import javax.inject.Inject

class SearchViewPagerEventsViewModelFactory
    @Inject
    constructor(
        val newsRepository: NewsRepository,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            SearchViewPagerEventsViewModel(
                newsRepository as NewsRepositoryImpl,
            ) as T
    }
