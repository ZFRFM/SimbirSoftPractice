package ru.faimizufarov.simbirtraining.java.di

import android.app.Application
import dagger.Component
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.main.MainActivity
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news.NewsFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DomainModule::class, DataModule::class])
interface AppComponent {
    fun inject(application: Application)

    fun injectMainActivity(mainActivity: MainActivity)

    fun injectNewsFragment(newsFragment: NewsFragment)

    @Component.Factory
    interface Factory {
        fun create(appModule: AppModule): AppComponent
    }
}
