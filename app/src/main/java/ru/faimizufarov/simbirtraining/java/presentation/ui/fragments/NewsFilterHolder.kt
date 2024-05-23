package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import ru.faimizufarov.simbirtraining.java.data.models.CategoryFilter

interface NewsFilterHolder {

    val activeFilters: List<CategoryFilter>
    val queuedFilters: List<CategoryFilter>

    fun setFilter(categoryId: String)

    fun removeFilter(categoryId: String)

    fun confirm()

    fun cancel()

    fun setOnFiltersSubmittedListener(listener: ((List<CategoryFilter>) -> Unit))

    fun setOnFiltersEditedListener(listener: ((List<CategoryFilter>) -> Unit))
}