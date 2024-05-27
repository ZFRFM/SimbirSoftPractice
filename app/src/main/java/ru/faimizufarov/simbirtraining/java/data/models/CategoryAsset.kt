package ru.faimizufarov.simbirtraining.java.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryAsset(
    @SerialName("id") val id: String,
    @SerialName("name_en") val globalName: String,
    @SerialName("name") val localizedName: String,
    @SerialName("image") val imagePath: String,
)
