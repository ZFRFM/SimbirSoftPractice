package ru.faimizufarov.simbirtraining.java

import android.app.Application
import ru.faimizufarov.simbirtraining.java.data.repository.CategoryRepositoryImpl
import ru.faimizufarov.simbirtraining.java.data.repository.NewsRepositoryImpl
import ru.faimizufarov.simbirtraining.java.presentation.ui.holders.GlobalNewsFilter

class App : Application() {
    val newsRepositoryImpl by lazy { NewsRepositoryImpl(applicationContext) }
    val categoriesRepository by lazy { CategoryRepositoryImpl(applicationContext) }
    val newsFilters by lazy { GlobalNewsFilter() }
}
