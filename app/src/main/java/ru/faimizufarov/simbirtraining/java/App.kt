package ru.faimizufarov.simbirtraining.java

import android.app.Application
import ru.faimizufarov.simbirtraining.java.data.repositories.CategoryRepository
import ru.faimizufarov.simbirtraining.java.data.repositories.NewsRepository

class App : Application() {
    val newsRepository by lazy { NewsRepository(applicationContext) }
    val categoriesRepository by lazy { CategoryRepository(applicationContext) }
}
