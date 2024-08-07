package ru.faimizufarov.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class News(
    val id: String,
    val nameText: String,
    val startDate: Long,
    val finishDate: Long,
    val descriptionText: String,
    val status: Long,
    val newsImages: List<String>,
    val categoryIds: List<String>,
    val createAt: Long,
    val phoneText: String,
    val addressText: String,
    val organisationText: String,
)
