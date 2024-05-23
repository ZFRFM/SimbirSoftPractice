package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import android.content.Context
import kotlinx.coroutines.delay
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.java.data.models.CategoryIdResponse
import ru.faimizufarov.simbirtraining.java.data.models.News
import ru.faimizufarov.simbirtraining.java.data.models.NewsResponse
import ru.faimizufarov.simbirtraining.java.data.models.categoryEnumFromId

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
                    newsImageUrl = it.newsImage,
                    nameText = it.nameText,
                    descriptionText = it.descriptionText,
                    remainingTimeText = R.string.news_remaining_time,
                    categoryIds = it.categoryIds.map(CategoryIdResponse::id),
                    startDate = it.startDate.toLocalDateTime(),
                    finishDate = it.finishDate.toLocalDateTime(),
                )
            }
}
