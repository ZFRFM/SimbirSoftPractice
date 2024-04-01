package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import ru.faimizufarov.simbirtraining.java.data.Category
import ru.faimizufarov.simbirtraining.java.data.HelpCategoryEnum

object NewsFilterHolder {
    private val listFiltersHolder =
        listOf(
            Category(enumValue = HelpCategoryEnum.CHILDREN, checked = true),
            Category(enumValue = HelpCategoryEnum.ADULTS, checked = true),
            Category(enumValue = HelpCategoryEnum.ELDERLY, checked = true),
            Category(enumValue = HelpCategoryEnum.ANIMALS, checked = true),
            Category(enumValue = HelpCategoryEnum.EVENTS, checked = true),
        )

    var onFiltersChangedListener: ((List<Category>) -> Unit)? = null

    fun getFilterList() = listFiltersHolder

    fun setFilter(
        category: Category,
        isFiltered: Boolean,
    ) {
        listFiltersHolder.firstOrNull { it == category }?.checked = isFiltered
    }

    fun setOnFilterChangedListener(listener: ((List<Category>) -> Unit)?) {
        onFiltersChangedListener = listener
    }
}
