package ru.faimizufarov.news.models

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

fun News.toNewsCompose() =
    NewsCompose(
        id = id,
        nameText = nameText,
        startDate = startDate,
        finishDate = finishDate,
        descriptionText = descriptionText,
        status = status,
        newsImages = newsImages,
        categoryIds = categoryIds,
        createAt = createAt,
        phoneText = phoneText,
        addressText = addressText,
        organisationText = organisationText,
    )

fun NewsCompose.toNews() =
    News(
        id = id,
        nameText = nameText,
        startDate = startDate,
        finishDate = finishDate,
        descriptionText = descriptionText,
        status = status,
        newsImages = newsImages,
        categoryIds = categoryIds,
        createAt = createAt,
        phoneText = phoneText,
        addressText = addressText,
        organisationText = organisationText,
    )
