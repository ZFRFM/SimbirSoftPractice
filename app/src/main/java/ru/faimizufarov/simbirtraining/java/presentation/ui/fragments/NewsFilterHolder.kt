package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import ru.faimizufarov.simbirtraining.java.data.models.CategoryFilter

interface NewsFilterHolder {

    val filters: List<CategoryFilter>
    fun setFilter(categoryFilter: CategoryFilter)

    fun removeFilter(categoryFilter: CategoryFilter)

    fun confirm()

    fun cancel()

    fun setOnFilterChangedListener(listener: ((List<CategoryFilter>) -> Unit))
}