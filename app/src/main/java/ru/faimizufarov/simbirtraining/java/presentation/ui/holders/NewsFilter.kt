package ru.faimizufarov.simbirtraining.java.presentation.ui.holders

import kotlinx.coroutines.flow.StateFlow
import ru.faimizufarov.simbirtraining.java.data.models.CategoryFilter

interface NewsFilter {
    val activeFiltersFlow: StateFlow<List<CategoryFilter>>
    val queuedFiltersFlow: StateFlow<List<CategoryFilter>>

    fun setFilter(categoryId: String)

    fun removeFilter(categoryId: String)

    fun confirmFilters()

    fun cancelFilters()
}
