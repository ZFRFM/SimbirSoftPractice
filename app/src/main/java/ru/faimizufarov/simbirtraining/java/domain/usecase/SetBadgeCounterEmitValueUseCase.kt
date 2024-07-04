package ru.faimizufarov.simbirtraining.java.domain.usecase

import ru.faimizufarov.simbirtraining.java.domain.repository.NewsRepository

class SetBadgeCounterEmitValueUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(emitValue: Int) {
        newsRepository.setBadgeCounterEmitValue(emitValue)
    }
}
