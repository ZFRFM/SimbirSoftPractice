package ru.faimizufarov.simbirtraining.java.data

import kotlinx.datetime.LocalDateTime

data class News(
    val imageViewNews: Int,
    val textViewName: Int,
    val textViewDescription: Int,
    val textViewRemainingTime: Int,
    val helpCategory: List<Category>,
    val startDate: LocalDateTime,
    val finishDate: LocalDateTime,
)
