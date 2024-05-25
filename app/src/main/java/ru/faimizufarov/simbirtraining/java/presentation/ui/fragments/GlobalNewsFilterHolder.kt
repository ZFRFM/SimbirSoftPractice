package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.faimizufarov.simbirtraining.java.data.models.CategoryFilter

object GlobalNewsFilterHolder : NewsFilterHolder {
    private val _activeFiltersFlow = MutableStateFlow(emptyList<CategoryFilter>())
    override val activeFiltersFlow: StateFlow<List<CategoryFilter>> = _activeFiltersFlow

    private val _queuedFiltersFlow = MutableStateFlow(emptyList<CategoryFilter>())
    override val queuedFiltersFlow: StateFlow<List<CategoryFilter>> = _queuedFiltersFlow

    override fun setFilter(categoryId: String) {
        val updatedFilters = _queuedFiltersFlow.value + CategoryFilter(categoryId)
        _queuedFiltersFlow.tryEmit(updatedFilters)
    }

    override fun removeFilter(categoryId: String) {
        val updatedFilters = _queuedFiltersFlow.value.filterNot { it.categoryId == categoryId }
        _queuedFiltersFlow.tryEmit(updatedFilters)
    }

    override fun confirm() {
        _activeFiltersFlow.tryEmit(_queuedFiltersFlow.value)
    }

    override fun cancel() {
        _queuedFiltersFlow.tryEmit(_activeFiltersFlow.value)
    }
}
