package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import ru.faimizufarov.simbirtraining.java.data.models.CategoryFilter

object GlobalNewsFilterHolder : NewsFilterHolder {
    private val _activeFilters = mutableListOf<CategoryFilter>()
    override val activeFilters: List<CategoryFilter> = _activeFilters

    private val _queuedFilters = mutableListOf<CategoryFilter>()
    override val queuedFilters: List<CategoryFilter> = _queuedFilters

    override fun setFilter(categoryId: String) {
        _queuedFilters.add(CategoryFilter(categoryId))
        onFiltersEditedListener(queuedFilters)
    }

    override fun removeFilter(categoryId: String) {
        _queuedFilters.removeIf { filter -> filter.categoryId == categoryId }
        onFiltersEditedListener(queuedFilters)
    }

    override fun confirm() {
        _activeFilters.clear()
        _activeFilters.addAll(queuedFilters)
        onFiltersSubmittedListener.invoke(activeFilters)
    }

    override fun cancel() {
        _queuedFilters.clear()
        _queuedFilters.addAll(activeFilters)
        onFiltersEditedListener.invoke(queuedFilters)
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
