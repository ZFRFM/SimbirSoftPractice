package ru.faimizufarov.domain.repository

interface NewsRepository {
    suspend fun requestNewsList(ids: List<String>)

    suspend fun setBadgeCounterEmitValue(emitValue: Int)
}
