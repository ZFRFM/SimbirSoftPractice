package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import ru.faimizufarov.simbirtraining.java.data.models.CategoryFilter
import ru.faimizufarov.simbirtraining.java.data.models.HelpCategoryEnum

object GlobalNewsFilterHolder : NewsFilterHolder {
    override val activeFilters = listOf(
        CategoryFilter(enumValue = HelpCategoryEnum.CHILDREN, checked = true),
        CategoryFilter(enumValue = HelpCategoryEnum.ADULTS, checked = true),
        CategoryFilter(enumValue = HelpCategoryEnum.ELDERLY, checked = true),
        CategoryFilter(enumValue = HelpCategoryEnum.ANIMALS, checked = true),
        CategoryFilter(enumValue = HelpCategoryEnum.EVENTS, checked = true),
    )

    override val queuedFilters = this.activeFilters.map(CategoryFilter::copy)

    override fun setFilter(categoryEnum: HelpCategoryEnum) {
        queuedFilters.firstOrNull { it.enumValue == categoryEnum }?.checked = true
        onFiltersEditedListener.invoke(queuedFilters)
    }

    override fun removeFilter(categoryEnum: HelpCategoryEnum) {
        queuedFilters.firstOrNull { it.enumValue == categoryEnum }?.checked = false
        onFiltersEditedListener.invoke(queuedFilters)
    }

    override fun confirm() {
        queuedFilters.forEach { queuedFilter ->
            val sameActiveFilter = activeFilters.firstOrNull { activeFilter ->
                activeFilter.enumValue == queuedFilter.enumValue
            }
            sameActiveFilter?.checked = queuedFilter.checked
        }
        onFiltersSubmittedListener.invoke(activeFilters)
    }

    override fun cancel() {
        activeFilters.forEach { active ->
            val sameQueuedFilter = activeFilters.firstOrNull { queuedFilter ->
                queuedFilter.enumValue == active.enumValue
            }
            sameQueuedFilter?.checked = active.checked
        }
        onFiltersSubmittedListener.invoke(activeFilters)
    }

    private var onFiltersSubmittedListener: (List<CategoryFilter>) -> Unit = {}

    override fun setOnFiltersSubmittedListener(listener: ((List<CategoryFilter>) -> Unit)) {
        onFiltersSubmittedListener = listener
    }

    private var onFiltersEditedListener: (List<CategoryFilter>) -> Unit = {}

    override fun setOnFiltersEditedListener(listener: (List<CategoryFilter>) -> Unit) {
        onFiltersEditedListener = listener
    }
}
