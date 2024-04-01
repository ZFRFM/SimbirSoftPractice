package ru.faimizufarov.simbirtraining.java.data

import kotlinx.datetime.LocalDateTime

data class News(
    val id: Int,
    val newsImageUrl: String,
    val nameText: String,
    val descriptionText: String,
    val remainingTimeText: Int,
    val helpCategory: List<Category>,
    val startDate: LocalDateTime,
    val finishDate: LocalDateTime,
)
