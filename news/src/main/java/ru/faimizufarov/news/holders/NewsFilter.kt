package ru.faimizufarov.news.holders

import kotlinx.coroutines.flow.StateFlow
import ru.faimizufarov.data.models.CategoryFilter

interface NewsFilter {
    val activeFiltersFlow: StateFlow<List<CategoryFilter>>
    val queuedFiltersFlow: StateFlow<List<CategoryFilter>>

    fun setFilter(categoryId: String)

    fun removeFilter(categoryId: String)

    fun confirmFilters()

    fun cancelFilters()
}
