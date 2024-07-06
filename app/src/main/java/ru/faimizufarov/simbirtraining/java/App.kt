package ru.faimizufarov.simbirtraining.java

import android.app.Application
import ru.faimizufarov.simbirtraining.java.data.repository.CategoryRepositoryImpl
import ru.faimizufarov.simbirtraining.java.data.repository.NewsRepositoryImpl
import ru.faimizufarov.simbirtraining.java.di.AppComponent
import ru.faimizufarov.simbirtraining.java.di.AppModule
import ru.faimizufarov.simbirtraining.java.di.DaggerAppComponent
import ru.faimizufarov.simbirtraining.java.presentation.ui.holders.GlobalNewsFilter

class App : Application() {
    val newsRepositoryImpl by lazy { NewsRepositoryImpl(applicationContext) }
    val categoriesRepository by lazy { CategoryRepositoryImpl(applicationContext) }
    val newsFilters by lazy { GlobalNewsFilter() }

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(AppModule(this))
        appComponent.inject(this)
    }
}
