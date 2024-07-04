package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news_filter

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import ru.faimizufarov.simbirtraining.java.App
import ru.faimizufarov.simbirtraining.java.data.models.CategoryFilter
import ru.faimizufarov.simbirtraining.java.data.models.CategoryFilterItem
import ru.faimizufarov.simbirtraining.java.domain.models.Category
import ru.faimizufarov.simbirtraining.java.domain.usecase.GetCategoriesUseCase
import ru.faimizufarov.simbirtraining.java.presentation.ui.holders.GlobalNewsFilter

class NewsFilterViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val newsFilter: GlobalNewsFilter,
) : ViewModel() {
    val categoryFiltersLiveData: LiveData<List<CategoryFilterItem>> =
        newsFilter.queuedFiltersFlow.map { filters ->
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
        newsFilter.removeFilter(categoryId)
    }

    fun setFilter(categoryId: String) {
        newsFilter.setFilter(categoryId)
    }

    fun confirmFilters() {
        newsFilter.confirmFilters()
    }

    fun cancelFilters() {
        newsFilter.cancelFilters()
    }

    class Factory(context: Context) : ViewModelProvider.Factory {
        private val categoryRepository =
            (context.applicationContext as App).categoriesRepository

        private val getCategoriesUseCase = GetCategoriesUseCase(categoryRepository)

        private val newsFilters = (context.applicationContext as App).newsFilters

        override fun <T : ViewModel> create(modelClass: Class<T>): T = NewsFilterViewModel(getCategoriesUseCase, newsFilters) as T
    }
}
