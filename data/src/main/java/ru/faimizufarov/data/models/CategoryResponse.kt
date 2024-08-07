package ru.faimizufarov.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    @SerialName("id") val id: String,
    @SerialName("name_en") val globalName: String,
    @SerialName("name") val localizedName: String,
    @SerialName("image") val imageUrl: String,
)
