package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.faimizufarov.domain.repository.NewsRepository
import ru.faimizufarov.simbirtraining.java.data.repository.NewsRepositoryImpl
import ru.faimizufarov.simbirtraining.java.domain.usecase.GetNewsUseCase
import ru.faimizufarov.simbirtraining.java.domain.usecase.SetBadgeCounterEmitValueUseCase
import ru.faimizufarov.simbirtraining.java.presentation.ui.holders.GlobalNewsFilter
import javax.inject.Inject

class NewsViewModelFactory
    @Inject
    constructor(
        val getNewsUseCase: GetNewsUseCase,
        val setBadgeCounterEmitValueUseCase: SetBadgeCounterEmitValueUseCase,
        val newsFilters: GlobalNewsFilter,
        val newsRepository: NewsRepository,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            NewsViewModel(
                getNewsUseCase,
                setBadgeCounterEmitValueUseCase,
                newsFilters,
                newsRepository as NewsRepositoryImpl,
            ) as T
    }
