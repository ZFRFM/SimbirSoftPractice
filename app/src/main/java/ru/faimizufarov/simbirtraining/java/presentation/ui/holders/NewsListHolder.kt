package ru.faimizufarov.simbirtraining.java.presentation.ui.holders

import ru.faimizufarov.simbirtraining.java.data.models.News

object NewsListHolder {
    private var newsListHolder = listOf<News>()

    fun getNewsList() = newsListHolder

    fun setNewsList(listOfNews: List<News>) {
        newsListHolder = listOfNews
    }
}
