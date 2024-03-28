package ru.faimizufarov.simbirtraining.java.data

import kotlinx.datetime.LocalDateTime
import ru.faimizufarov.simbirtraining.R

data class News(
    val imageViewNews: String,
    val textViewName: String,
    val textViewDescription: String,
    val textViewRemainingTime: Int = R.string.news_remaining_time,
    val helpCategory: List<Category>,
    val startDate: LocalDateTime,
    val finishDate: LocalDateTime,
)
