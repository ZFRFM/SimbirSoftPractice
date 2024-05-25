package ru.faimizufarov.simbirtraining.java.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val nameText: String,
    @SerialName("startDate")
    val startDate: Long,
    @SerialName("endDate")
    val finishDate: Long,
    @SerialName("description")
    val descriptionText: String,
    @SerialName("status")
    val status: Long,
    @SerialName("photos")
    val newsImages: List<String>,
    @SerialName("categories")
    val categoryIds: List<String>,
    @SerialName("createAt")
    val createAt: Long,
    @SerialName("phone")
    val phone: String,
    @SerialName("address")
    val address: String,
    @SerialName("organisation")
    val organisation: String,
)

fun NewsResponse.mapToNews() =
    News(
        id,
        nameText,
        startDate,
        finishDate,
        descriptionText,
        status,
        newsImages,
        categoryIds,
        createAt,
        phone,
        address,
        organisation,
    )
