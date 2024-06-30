package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news_filter

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.faimizufarov.simbirtraining.java.App
import ru.faimizufarov.simbirtraining.java.data.models.CategoryFilterItem
import ru.faimizufarov.simbirtraining.java.data.repositories.CategoryRepository
import ru.faimizufarov.simbirtraining.java.presentation.ui.holders.GlobalNewsFilter

class NewsFilterViewModel(
    private val categoriesRepository: CategoryRepository,
    newsFilters: GlobalNewsFilter,
) : ViewModel() {
    private val _categoryFiltersLiveData = MutableLiveData<List<CategoryFilterItem>>()
    val categoryFiltersLiveData: LiveData<List<CategoryFilterItem>> = _categoryFiltersLiveData

    val newsFilter: LiveData<GlobalNewsFilter> = MutableLiveData(newsFilters)

    fun removeFilter(categoryId: String) {
        newsFilter.value?.removeFilter(categoryId)
    }

    fun setFilter(categoryId: String) {
        newsFilter.value?.setFilter(categoryId)
    }

    fun confirmFilters() {
        newsFilter.value?.confirmFilters()
    }

    fun cancelFilters() {
        newsFilter.value?.cancelFilters()
    }

    fun collectNewsFilters() {
        viewModelScope.launch {
            newsFilter.value?.queuedFiltersFlow?.collect { filters ->
                val categories = categoriesRepository.getCategoryList()

                val categoryFilterList =
                    categories.map { category ->
                        CategoryFilterItem(
                            categoryId = category.id,
                            title = category.title,
                            isChecked =
                                filters.any { filter ->
                                    filter.categoryId == category.id
                                },
                        )
                    }

                _categoryFiltersLiveData.value = categoryFilterList
            }
        }
    }

    class Factory(context: Context) : ViewModelProvider.Factory {
        private val categoryRepository =
            (context.applicationContext as App).categoriesRepository

        private val newsFilters = (context.applicationContext as App).newsFilters

        override fun <T : ViewModel> create(modelClass: Class<T>): T = NewsFilterViewModel(categoryRepository, newsFilters) as T
    }
}
