package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news_filter

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.faimizufarov.simbirtraining.java.App
import ru.faimizufarov.simbirtraining.java.data.models.Category
import ru.faimizufarov.simbirtraining.java.data.repositories.CategoryRepository

class NewsFilterViewModel(
    private val categoriesRepository: CategoryRepository,
) : ViewModel() {
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    suspend fun getCategoryList() {
        _categories.value = categoriesRepository.getCategoryList()
    }

    class Factory(context: Context) : ViewModelProvider.Factory {
        private val categoryRepository =
            (context.applicationContext as App).categoriesRepository

        override fun <T : ViewModel> create(modelClass: Class<T>): T = NewsFilterViewModel(categoryRepository) as T
    }
}
