package ru.faimizufarov.simbirtraining.java.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsJsonRepresentation(
    val id: Int,
    @SerialName("event_image")
    val imageViewNews: String,
    @SerialName("event_name_text")
    val textViewName: String,
    @SerialName("event_description_text")
    val textViewDescription: String,
    @SerialName("help_category")
    val helpCategory: List<HelpCategoryEnumJsonRepresentation>,
    @SerialName("start_date")
    val startDate: String,
    @SerialName("finish_date")
    val finishDate: String,
)
