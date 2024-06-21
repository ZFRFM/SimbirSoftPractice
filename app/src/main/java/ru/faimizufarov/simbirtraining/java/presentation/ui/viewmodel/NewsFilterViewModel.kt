package ru.faimizufarov.simbirtraining.java.presentation.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import ru.faimizufarov.simbirtraining.java.data.repositories.CategoryRepository

class NewsFilterViewModel(
    context: Context,
) : ViewModel() {
    private val _categoriesRepository = MutableLiveData(CategoryRepository(context))
    val categoriesRepository: LiveData<CategoryRepository>
        get() = _categoriesRepository

    companion object {
        val Factory: ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras,
                ): T {
                    val application = checkNotNull(extras[APPLICATION_KEY])

                    return NewsFilterViewModel(
                        application.baseContext.applicationContext,
                    ) as T
                }
            }
    }
}
