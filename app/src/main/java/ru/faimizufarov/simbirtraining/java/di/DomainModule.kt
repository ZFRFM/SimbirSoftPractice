package ru.faimizufarov.simbirtraining.java.di

import dagger.Module
import dagger.Provides
import ru.faimizufarov.domain.repository.CategoryRepository
import ru.faimizufarov.domain.repository.NewsRepository
import ru.faimizufarov.domain.usecase.GetCategoriesUseCase
import ru.faimizufarov.domain.usecase.GetNewsUseCase
import ru.faimizufarov.domain.usecase.SetBadgeCounterEmitValueUseCase

@Module
class DomainModule {
    @Provides
    fun provideGetNewsUseCase(newsRepository: NewsRepository) = GetNewsUseCase(newsRepository)

    @Provides
    fun provideGetCategoriesUseCase(categoryRepository: CategoryRepository) = GetCategoriesUseCase(categoryRepository)

    @Provides
    fun provideSetBadgeCounterEmitValueUseCase(newsRepository: NewsRepository) = SetBadgeCounterEmitValueUseCase(newsRepository)
}
