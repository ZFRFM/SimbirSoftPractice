package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import ru.faimizufarov.simbirtraining.java.data.models.CategoryFilter
import ru.faimizufarov.simbirtraining.java.data.models.HelpCategoryEnum

object GlobalNewsFilterHolder : NewsFilterHolder {
    private val activeFilters = listOf(
        CategoryFilter(enumValue = HelpCategoryEnum.CHILDREN, checked = true),
        CategoryFilter(enumValue = HelpCategoryEnum.ADULTS, checked = true),
        CategoryFilter(enumValue = HelpCategoryEnum.ELDERLY, checked = true),
        CategoryFilter(enumValue = HelpCategoryEnum.ANIMALS, checked = true),
        CategoryFilter(enumValue = HelpCategoryEnum.EVENTS, checked = true),
    )

    override val filters: List<CategoryFilter>
        get() = activeFilters

    private val queuedFilters = activeFilters.map(CategoryFilter::copy)

    override fun setFilter(categoryFilter: CategoryFilter) {
        queuedFilters.firstOrNull { it == categoryFilter }?.checked = true
    }

    override fun removeFilter(categoryFilter: CategoryFilter) {
        queuedFilters.firstOrNull { it == categoryFilter }?.checked = false
    }

    override fun confirm() {
        queuedFilters.forEach { queuedFilter ->
            val sameActiveFilter = activeFilters.firstOrNull { activeFilter ->
                activeFilter.enumValue == queuedFilter.enumValue
            }
            sameActiveFilter?.checked = queuedFilter.checked
        }
        onFiltersChangedListener.invoke(activeFilters)
    }

    override fun cancel() {
        activeFilters.forEach { active ->
            val sameQueuedFilter = activeFilters.firstOrNull { queuedFilter ->
                queuedFilter.enumValue == active.enumValue
            }
            sameQueuedFilter?.checked = active.checked
        }
        onFiltersChangedListener.invoke(activeFilters)
    }

    private var onFiltersChangedListener: ((List<CategoryFilter>) -> Unit) = {}

    override fun setOnFilterChangedListener(listener: ((List<CategoryFilter>) -> Unit)) {
        onFiltersChangedListener = listener
    }
}
