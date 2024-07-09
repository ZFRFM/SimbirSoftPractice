package ru.faimizufarov.simbirtraining.java

import android.app.Application
import ru.faimizufarov.simbirtraining.java.di.AppComponent
import ru.faimizufarov.simbirtraining.java.di.AppModule
import ru.faimizufarov.simbirtraining.java.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(AppModule(this))
        appComponent.inject(this)
    }
}
