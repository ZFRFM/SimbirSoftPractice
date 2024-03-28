package ru.faimizufarov.simbirtraining.java.data

import kotlinx.serialization.Serializable

@Serializable
data class NewsJsonRepresentation(
    val id: Int,
    val imageViewNews: String,
    val textViewName: String,
    val textViewDescription: String,
    val helpCategory: List<HelpCategoryEnumJsonRepresentation>,
    val startDate: String,
    val finishDate: String,
)
