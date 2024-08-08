package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news_filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import ru.faimizufarov.data.models.CategoryFilter
import ru.faimizufarov.data.models.CategoryFilterItem
import ru.faimizufarov.domain.models.Category
import ru.faimizufarov.domain.usecase.GetCategoriesUseCase
import ru.faimizufarov.news.holders.GlobalNewsFilter

class NewsFilterViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val newsFilters: GlobalNewsFilter,
) : ViewModel() {
    val categoryFiltersLiveData: LiveData<List<CategoryFilterItem>> =
        newsFilters.queuedFiltersFlow.map { filters ->
            val categories = getCategoriesUseCase.execute()
            categories.toFilteredItems(filters)
        }.asLiveData(Dispatchers.IO)

    private fun List<Category>.toFilteredItems(filters: List<CategoryFilter>) =
        map { category ->
            CategoryFilterItem(
                categoryId = category.id,
                title = category.title,
                isChecked =
                    filters.any { filter ->
                        filter.categoryId == category.id
                    },
            )
        }

    fun removeFilter(categoryId: String) {
        newsFilters.removeFilter(categoryId)
    }

    fun setFilter(categoryId: String) {
        newsFilters.setFilter(categoryId)
    }

    fun confirmFilters() {
        newsFilters.confirmFilters()
    }

    fun cancelFilters() {
        newsFilters.cancelFilters()
    }
}
