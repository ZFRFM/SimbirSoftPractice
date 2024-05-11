package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import android.content.Context
import kotlinx.coroutines.delay
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.java.data.Category
import ru.faimizufarov.simbirtraining.java.data.HelpCategoryEnum
import ru.faimizufarov.simbirtraining.java.data.News
import ru.faimizufarov.simbirtraining.java.data.NewsResponse

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
            .open("news_list.json")
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
                    helpCategory =
                        it.helpCategory.map {
                            Category(
                                enumValue =
                                    when (it.id) {
                                        0 -> HelpCategoryEnum.CHILDREN
                                        1 -> HelpCategoryEnum.ADULTS
                                        2 -> HelpCategoryEnum.ELDERLY
                                        3 -> HelpCategoryEnum.ANIMALS
                                        4 -> HelpCategoryEnum.EVENTS
                                        else -> error("Unknown category")
                                    },
                                checked = it.checked,
                            )
                        },
                    startDate = it.startDate.toLocalDateTime(),
                    finishDate = it.finishDate.toLocalDateTime(),
                )
            }
}
