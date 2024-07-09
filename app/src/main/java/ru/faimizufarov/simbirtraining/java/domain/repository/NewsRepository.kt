package ru.faimizufarov.simbirtraining.java.domain.repository

interface NewsRepository {
    suspend fun requestNewsList(ids: List<String>)

    suspend fun setBadgeCounterEmitValue(emitValue: Int)
}
