package ru.faimizufarov.simbirtraining.java.data.repositories

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.faimizufarov.simbirtraining.java.data.local.AppDatabase
import ru.faimizufarov.simbirtraining.java.data.local.toNews
import ru.faimizufarov.simbirtraining.java.data.local.toNewsEntity
import ru.faimizufarov.simbirtraining.java.data.models.News
import ru.faimizufarov.simbirtraining.java.data.models.mapToNews
import ru.faimizufarov.simbirtraining.java.data.network.AppApi

class NewsRepository(context: Context) {
    private val database = AppDatabase.getDatabase(context)

    // FIXME: Bad code actually. Could've add return type to requestNewsList, but decided to
    //  be lazy, because now I don't have to mess with scope in SearchViewPager. So it's your
    //  job to make it proper
    private val _newsListFlow = MutableStateFlow(emptyList<News>())
    val newsListFlow: StateFlow<List<News>> = _newsListFlow

    suspend fun requestNewsList(ids: List<String>) {
        val newsList =
            if (isNewsCached()) {
                loadServerNewsFromNetwork(ids)
            } else {
                loadServerNewsFromDatabase(ids)
            }
        _newsListFlow.emit(newsList)
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
