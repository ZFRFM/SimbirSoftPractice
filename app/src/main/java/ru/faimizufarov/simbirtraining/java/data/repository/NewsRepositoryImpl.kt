package ru.faimizufarov.simbirtraining.java.data.repository

import android.content.Context
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import ru.faimizufarov.data.models.NewsAsset
import ru.faimizufarov.data.models.NewsResponse
import ru.faimizufarov.data.network.AppApi
import ru.faimizufarov.domain.models.News
import ru.faimizufarov.domain.repository.NewsRepository
import ru.faimizufarov.simbirtraining.java.data.local.AppDatabase
import ru.faimizufarov.simbirtraining.java.data.local.toNews
import ru.faimizufarov.simbirtraining.java.data.local.toNewsEntity
import java.io.BufferedReader
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NewsRepositoryImpl(
    private val context: Context,
) : NewsRepository {
    private val database = AppDatabase.getDatabase(context)
    private val assetManager = context.assets

    private val _newsListFlow = MutableStateFlow(emptyList<News>())
    val newsListFlow: StateFlow<List<News>> = _newsListFlow

    private val _badgeCounterFlow = MutableStateFlow(0)
    val badgeCounterFlow: StateFlow<Int> = _badgeCounterFlow

    override suspend fun requestNewsList(ids: List<String>) {
        val newsList =
            try {
                withTimeout(2500) {
                    getNewsFromApi(ids)
                }
            } catch (httpException: HttpException) {
                loadNewsInOfflineMode(ids)
            } catch (timeoutCancellationException: TimeoutCancellationException) {
                loadNewsInOfflineMode(ids)
            }

        _newsListFlow.emit(newsList)
    }

    private suspend fun getNewsFromApi(ids: List<String>) =
        if (isNewsCached()) {
            loadServerNewsFromDatabase(ids)
        } else {
            loadServerNewsFromNetwork(ids)
        }

    private suspend fun loadNewsInOfflineMode(ids: List<String>) =
        if (isNewsCached()) {
            loadServerNewsFromDatabase(ids)
        } else {
            getNewsFromAssets()
        }

    private suspend fun isNewsCached() = database.newsDao().checkNewsCount() != 0

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

    private suspend fun getNewsFromAssets() =
        suspendCoroutine { continuation ->
            val assetsReader =
                assetManager
                    .open("responses/news_list.json")
                    .bufferedReader()
            val newsJson = assetsReader.use(BufferedReader::readText)
            val newsAssets = Json.decodeFromString<Array<NewsAsset>>(newsJson)
            val news =
                newsAssets.toList().map { newsAsset ->
                    newsAsset.toNews()
                }
            continuation.resume(news)
        }

    private fun NewsAsset.toNews() =
        News(
            id = id,
            nameText = name,
            startDate = startDate,
            finishDate = endDate,
            descriptionText = description,
            status = status,
            newsImages = photos,
            categoryIds = categories,
            createAt = createAt,
            phoneText = phone,
            addressText = address,
            organisationText = organisation,
        )

    private fun NewsResponse.mapToNews() =
        News(
            id,
            nameText,
            startDate,
            finishDate,
            descriptionText,
            status,
            newsImages,
            categoryIds,
            createAt,
            phone,
            address,
            organisation,
        )

    override suspend fun setBadgeCounterEmitValue(emitValue: Int) {
        _badgeCounterFlow.emit(emitValue)
    }
}
