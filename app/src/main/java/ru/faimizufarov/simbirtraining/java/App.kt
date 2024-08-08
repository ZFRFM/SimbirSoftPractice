package ru.faimizufarov.simbirtraining.java

import android.app.Application
import ru.faimizufarov.news.di.NewsComponent
import ru.faimizufarov.news.di.NewsComponentProvider
import ru.faimizufarov.simbirtraining.java.di.AppComponent
import ru.faimizufarov.simbirtraining.java.di.AppModule
import ru.faimizufarov.simbirtraining.java.di.DaggerAppComponent

class App : Application(), NewsComponentProvider {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(AppModule(this))
        appComponent.inject(this)
    }

    override fun provideNewsComponent(): NewsComponent = appComponent
}
