package ru.faimizufarov.simbirtraining.java.presentation.ui.holders

import android.content.Context
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import ru.faimizufarov.simbirtraining.java.data.models.News
import ru.faimizufarov.simbirtraining.java.data.models.NewsResponse

object NewsListHolder {
    private var newsListHolder = listOf<News>()

    fun getNewsList() = newsListHolder

    fun setNewsList(listOfNews: List<News>) {
        newsListHolder = listOfNews
    }

    suspend fun getNewsJson(context: Context): String {
        delay(1000)
        return context
            .applicationContext
            .assets
            .open("responses/news_list.json")
            .bufferedReader()
            .use { it.readText() }
    }

    fun getNewsListFromJson(json: String) =
        Json
            .decodeFromString<Array<NewsResponse>>(json)
            .map { it ->
                News(
                    id = it.id,
                    nameText = it.nameText,
                    startDate = it.startDate,
                    finishDate = it.finishDate,
                    descriptionText = it.descriptionText,
                    status = it.status,
                    newsImages = it.newsImages,
                    categoryIds = it.categoryIds,
                    createAt = it.createAt,
                    phoneText = it.phone,
                    addressText = it.address,
                    organisationText = it.organisation,
                )
            }
}
