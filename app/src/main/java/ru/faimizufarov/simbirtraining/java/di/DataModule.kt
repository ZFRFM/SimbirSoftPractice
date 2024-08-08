package ru.faimizufarov.simbirtraining.java.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.faimizufarov.data.repository.CategoryRepositoryImpl
import ru.faimizufarov.data.repository.NewsRepositoryImpl
import ru.faimizufarov.domain.repository.CategoryRepository
import ru.faimizufarov.domain.repository.NewsRepository
import ru.faimizufarov.news.holders.GlobalNewsFilter
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideNewsRepository(context: Context): NewsRepository = NewsRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideCategoryRepository(context: Context): CategoryRepository = CategoryRepositoryImpl(context)

    @Provides
    @Singleton
    fun provideNewsFilters() = GlobalNewsFilter()
}
