package ru.faimizufarov.simbirtraining.java.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.faimizufarov.domain.repository.NewsRepository
import ru.faimizufarov.simbirtraining.java.domain.usecase.GetNewsUseCase
import ru.faimizufarov.simbirtraining.java.domain.usecase.SetBadgeCounterEmitValueUseCase
import ru.faimizufarov.simbirtraining.java.presentation.ui.holders.GlobalNewsFilter
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news.NewsViewModelFactory
import javax.inject.Singleton

@Module
class AppModule(val context: Context) {
    @Provides
    @Singleton
    fun provideContext() = context

    @Provides
    fun provideNewsViewModelFactory(
        getNewsUseCase: GetNewsUseCase,
        setBadgeCounterEmitValueUseCase: SetBadgeCounterEmitValueUseCase,
        newsFilter: GlobalNewsFilter,
        newsRepository: NewsRepository,
    ) = NewsViewModelFactory(
        getNewsUseCase = getNewsUseCase,
        setBadgeCounterEmitValueUseCase = setBadgeCounterEmitValueUseCase,
        newsFilter,
        newsRepository,
    )
}
