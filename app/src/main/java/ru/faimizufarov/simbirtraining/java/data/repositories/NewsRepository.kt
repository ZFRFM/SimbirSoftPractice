package ru.faimizufarov.simbirtraining.java.data.repositories

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.faimizufarov.simbirtraining.java.data.models.News
import ru.faimizufarov.simbirtraining.java.data.models.mapToNews
import ru.faimizufarov.simbirtraining.java.database.AppDatabase
import ru.faimizufarov.simbirtraining.java.database.toNews
import ru.faimizufarov.simbirtraining.java.database.toNewsEntity
import ru.faimizufarov.simbirtraining.java.network.AppApi

class NewsRepository(context: Context) {
    private val database = AppDatabase.getDatabase(context)

    suspend fun getNewsList(ids: List<String>) = isNewsCached(ids)

    private suspend fun isNewsCached(ids: List<String>): List<News> {
        return if (database.newsDao().checkNewsCount() == 0) {
            loadServerNewsFromNetwork(ids)
        } else {
            loadServerNewsFromDatabase(ids)
        }
    }

    private suspend fun loadServerNewsFromNetwork(ids: List<String>) =
        withContext(Dispatchers.IO) {
            val localServerNews = AppApi.retrofitService.getEvents(ids).map { it.mapToNews() }
            database.newsDao().insertNews(localServerNews.map { it.toNewsEntity() })
            localServerNews
        }

    private suspend fun loadServerNewsFromDatabase(ids: List<String>) =
        withContext(Dispatchers.IO) {
            val databaseNews = database.newsDao().getAllNews().map { it.toNews() }
            val filteredDatabaseNews =
                databaseNews.filter {
                    if (ids.isEmpty()) {
                        return@filter true
                    } else {
                        it.categoryIds.any { it in ids }
                    }
                }
            filteredDatabaseNews
        }
}
