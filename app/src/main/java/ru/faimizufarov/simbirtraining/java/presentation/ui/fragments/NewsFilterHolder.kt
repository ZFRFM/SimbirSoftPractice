package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import ru.faimizufarov.simbirtraining.java.data.models.CategoryFilter
import ru.faimizufarov.simbirtraining.java.data.models.HelpCategoryEnum

object NewsFilterHolder {
    private val listFiltersHolder =
        listOf(
            CategoryFilter(enumValue = HelpCategoryEnum.CHILDREN, checked = true),
            CategoryFilter(enumValue = HelpCategoryEnum.ADULTS, checked = true),
            CategoryFilter(enumValue = HelpCategoryEnum.ELDERLY, checked = true),
            CategoryFilter(enumValue = HelpCategoryEnum.ANIMALS, checked = true),
            CategoryFilter(enumValue = HelpCategoryEnum.EVENTS, checked = true),
        )

    var onFiltersChangedListener: ((List<CategoryFilter>) -> Unit)? = null

    fun getFilterList() = listFiltersHolder

    fun setFilter(
        categoryFilter: CategoryFilter,
        isFiltered: Boolean,
    ) {
        listFiltersHolder.firstOrNull { it == categoryFilter }?.checked = isFiltered
    }

    fun setOnFilterChangedListener(listener: ((List<CategoryFilter>) -> Unit)?) {
        onFiltersChangedListener = listener
    }
}
