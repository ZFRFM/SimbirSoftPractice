package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.faimizufarov.simbirtraining.java.presentation.models.CategoryPresentation

class CategoriesViewModel : ViewModel() {
    private val _categoriesLiveData = MutableLiveData<List<CategoryPresentation>>()
    val categoriesLiveData: LiveData<List<CategoryPresentation>> = _categoriesLiveData

    fun setCategories(categories: List<CategoryPresentation>) {
        _categoriesLiveData.value = categories
    }
}
