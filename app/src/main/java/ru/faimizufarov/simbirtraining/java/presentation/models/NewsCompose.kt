package ru.faimizufarov.simbirtraining.java.presentation.models

import androidx.compose.runtime.Immutable
import ru.faimizufarov.domain.models.News

@Immutable
data class NewsCompose(
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