package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news_filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.faimizufarov.domain.usecase.GetCategoriesUseCase
import ru.faimizufarov.news.holders.GlobalNewsFilter
import javax.inject.Inject

class NewsFilterViewModelFactory
    @Inject
    constructor(
        val getCategoriesUseCase: GetCategoriesUseCase,
        val newsFilters: GlobalNewsFilter,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            NewsFilterViewModel(
                getCategoriesUseCase = getCategoriesUseCase,
                newsFilters = newsFilters,
            ) as T
    }
