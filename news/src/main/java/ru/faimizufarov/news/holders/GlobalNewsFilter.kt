package ru.faimizufarov.news.holders

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.faimizufarov.data.models.CategoryFilter

class GlobalNewsFilter : NewsFilter {
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

    override fun confirmFilters() {
        _activeFiltersFlow.tryEmit(_queuedFiltersFlow.value)
    }

    override fun cancelFilters() {
        _queuedFiltersFlow.tryEmit(_activeFiltersFlow.value)
    }
}
