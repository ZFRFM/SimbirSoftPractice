package ru.faimizufarov.simbirtraining.java.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val id: Int,
    @SerialName("event_image")
    val newsImage: String,
    @SerialName("event_name_text")
    val nameText: String,
    @SerialName("event_description_text")
    val descriptionText: String,
    @SerialName("help_category")
    val helpCategory: List<HelpCategoryEnumResponse>,
    @SerialName("start_date")
    val startDate: String,
    @SerialName("finish_date")
    val finishDate: String,
)
