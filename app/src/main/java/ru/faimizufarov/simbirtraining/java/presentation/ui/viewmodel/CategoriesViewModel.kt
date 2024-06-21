package ru.faimizufarov.simbirtraining.java.presentation.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.faimizufarov.simbirtraining.java.data.models.Category

class CategoriesViewModel : ViewModel() {
    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>>
        get() = _categories

    fun setCategories(categories: List<Category>) {
        _categories.value = categories
    }
}
