package ru.faimizufarov.simbirtraining.java.domain.usecase

import ru.faimizufarov.domain.repository.NewsRepository

class SetBadgeCounterEmitValueUseCase(val newsRepository: NewsRepository) {
    suspend fun execute(emitValue: Int) {
        newsRepository.setBadgeCounterEmitValue(emitValue)
    }
}
