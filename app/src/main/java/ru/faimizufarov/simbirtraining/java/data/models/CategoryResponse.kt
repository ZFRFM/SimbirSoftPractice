package ru.faimizufarov.simbirtraining.java.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    @SerialName("id") val id: String,
    @SerialName("localized_name") val localizedName: String,
    @SerialName("global_name") val globalName: String,
    @SerialName("image_path") val imagePath: String,
)
