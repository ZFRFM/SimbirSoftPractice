package ru.faimizufarov.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsAsset(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("startDate") val startDate: Long,
    @SerialName("endDate") val endDate: Long,
    @SerialName("description") val description: String,
    @SerialName("status") val status: Long,
    @SerialName("photos") val photos: List<String>,
    @SerialName("categories") val categories: List<String>,
    @SerialName("createAt") val createAt: Long,
    @SerialName("phone") val phone: String,
    @SerialName("address") val address: String,
    @SerialName("organisation") val organisation: String,
)
