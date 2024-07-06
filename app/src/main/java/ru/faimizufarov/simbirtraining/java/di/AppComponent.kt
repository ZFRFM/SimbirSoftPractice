package ru.faimizufarov.simbirtraining.java.di

import android.app.Application
import dagger.Component
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.main.MainActivity
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news.NewsFragment
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news_filter.NewsFilterFragment
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.search.SearchViewPagerEventsFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DomainModule::class, DataModule::class])
interface AppComponent {
    fun inject(application: Application)

    fun injectMainActivity(mainActivity: MainActivity)

    fun injectNewsFragment(newsFragment: NewsFragment)

    fun injectNewsFilterFragment(newsFilterFragment: NewsFilterFragment)

    fun injectSearchViewPagerEventsFragment(searchViewPagerEventsFragment: SearchViewPagerEventsFragment)

    @Component.Factory
    interface Factory {
        fun create(appModule: AppModule): AppComponent
    }
}
