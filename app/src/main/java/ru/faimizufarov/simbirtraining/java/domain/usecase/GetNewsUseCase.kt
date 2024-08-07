package ru.faimizufarov.simbirtraining.java.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.faimizufarov.domain.repository.NewsRepository

class GetNewsUseCase(val newsRepository: NewsRepository) {
    suspend fun execute(ids: List<String>) =
        withContext(Dispatchers.IO) {
            newsRepository.requestNewsList(ids)
        }
}
