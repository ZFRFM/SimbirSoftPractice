package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import ru.faimizufarov.simbirtraining.java.data.models.CategoryFilter
import ru.faimizufarov.simbirtraining.java.data.models.HelpCategoryEnum

interface NewsFilterHolder {
    val filters: List<CategoryFilter>

    fun setFilter(categoryEnum: HelpCategoryEnum)

    fun removeFilter(categoryEnum: HelpCategoryEnum)

    fun confirm()

    fun cancel()

    fun setOnFilterChangedListener(listener: ((List<CategoryFilter>) -> Unit))
}
