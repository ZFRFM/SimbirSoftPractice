package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import ru.faimizufarov.simbirtraining.java.data.models.CategoryFilter
import ru.faimizufarov.simbirtraining.java.data.models.HelpCategoryEnum

interface NewsFilterHolder {
    val activeFilters: List<CategoryFilter>
    val queuedFilters: List<CategoryFilter>

    fun setFilter(categoryEnum: HelpCategoryEnum)

    fun removeFilter(categoryEnum: HelpCategoryEnum)

    fun confirm()

    fun cancel()

    fun setOnFiltersSubmittedListener(listener: ((List<CategoryFilter>) -> Unit))

    fun setOnFiltersEditedListener(listener: ((List<CategoryFilter>) -> Unit))
}
