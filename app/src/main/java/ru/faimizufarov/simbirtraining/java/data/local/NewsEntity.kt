package ru.faimizufarov.simbirtraining.java.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.faimizufarov.simbirtraining.java.data.models.News

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey val id: String,
    @ColumnInfo("name_text") val nameText: String,
    @ColumnInfo("start_date") val startDate: Long,
    @ColumnInfo("finish_date") val finishDate: Long,
    @ColumnInfo("description_text") val descriptionText: String,
    @ColumnInfo("status") val status: Long,
    @ColumnInfo("news_images") val newsImages: List<String>,
    @ColumnInfo("category_ids") val categoryIds: List<String>,
    @ColumnInfo("create_at") val createAt: Long,
    @ColumnInfo("phone_text") val phoneText: String,
    @ColumnInfo("address_text") val addressText: String,
    @ColumnInfo("organisation_text") val organisationText: String,
)

fun NewsEntity.toNews() =
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
