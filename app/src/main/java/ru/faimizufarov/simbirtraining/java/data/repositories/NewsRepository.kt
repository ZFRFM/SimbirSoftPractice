package ru.faimizufarov.simbirtraining.java.data.repositories

import android.content.Context
import ru.faimizufarov.simbirtraining.java.data.database.AppDatabase
import ru.faimizufarov.simbirtraining.java.data.database.toNews
import ru.faimizufarov.simbirtraining.java.data.database.toNewsEntity
import ru.faimizufarov.simbirtraining.java.data.models.News
import ru.faimizufarov.simbirtraining.java.data.models.mapToNews
import ru.faimizufarov.simbirtraining.java.data.network.AppApi

class NewsRepository(context: Context) {
    private val database = AppDatabase.getDatabase(context)

    suspend fun getNewsList(ids: List<String>) =
        if (isNewsCached()) {
            loadServerNewsFromNetwork(ids)
        } else {
            loadServerNewsFromDatabase(ids)
        }

    private suspend fun isNewsCached() = database.newsDao().checkNewsCount() == 0

    private suspend fun loadServerNewsFromNetwork(ids: List<String>): List<News> {
        val localServerNews = AppApi.retrofitService.getEvents(ids).map { it.mapToNews() }
        database.newsDao().insertNews(localServerNews.map { it.toNewsEntity() })
        return localServerNews
    }

    private suspend fun loadServerNewsFromDatabase(ids: List<String>): List<News> {
        val databaseNews = database.newsDao().getAllNews().map { it.toNews() }
        val filteredDatabaseNews =
            databaseNews.filter {
                if (ids.isEmpty()) {
                    return@filter true
                } else {
                    it.categoryIds.any { it in ids }
                }
            }
        return filteredDatabaseNews
    }
}
