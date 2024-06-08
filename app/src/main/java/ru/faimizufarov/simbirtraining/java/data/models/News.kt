package ru.faimizufarov.simbirtraining.java.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.faimizufarov.simbirtraining.java.database.NewsEntity

@Parcelize
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
) : Parcelable

fun News.toNewsEntity() =
    NewsEntity(
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
