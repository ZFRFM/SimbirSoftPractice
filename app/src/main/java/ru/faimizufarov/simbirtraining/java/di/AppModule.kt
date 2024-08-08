package ru.faimizufarov.simbirtraining.java.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.faimizufarov.domain.repository.NewsRepository
import ru.faimizufarov.domain.usecase.GetNewsUseCase
import ru.faimizufarov.domain.usecase.SetBadgeCounterEmitValueUseCase
import ru.faimizufarov.news.NewsViewModelFactory
import ru.faimizufarov.news.holders.GlobalNewsFilter
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
