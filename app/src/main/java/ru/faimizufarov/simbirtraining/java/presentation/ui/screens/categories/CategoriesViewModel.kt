package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.faimizufarov.simbirtraining.java.domain.models.Category

class CategoriesViewModel : ViewModel() {
    private val _categoriesLiveData = MutableLiveData<List<Category>>()
    val categoriesLiveData: LiveData<List<Category>> = _categoriesLiveData

    fun setCategories(categories: List<Category>) {
        _categoriesLiveData.value = categories
    }
}
