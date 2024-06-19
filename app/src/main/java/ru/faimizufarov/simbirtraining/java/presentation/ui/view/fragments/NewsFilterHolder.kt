package ru.faimizufarov.simbirtraining.java.presentation.ui.view.fragments

import kotlinx.coroutines.flow.StateFlow
import ru.faimizufarov.simbirtraining.java.data.models.CategoryFilter

interface NewsFilterHolder {
    val activeFiltersFlow: StateFlow<List<CategoryFilter>>
    val queuedFiltersFlow: StateFlow<List<CategoryFilter>>

    fun setFilter(categoryId: String)

    fun removeFilter(categoryId: String)

    fun confirm()

    fun cancel()
}
