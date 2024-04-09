package ru.faimizufarov.simbirtraining.java.data

import android.os.Parcelable
import kotlinx.datetime.LocalDateTime
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class News(
    val id: Int,
    val newsImageUrl: String,
    val nameText: String,
    val descriptionText: String,
    val remainingTimeText: Int,
    val helpCategory: List<Category>,
    val startDate: @RawValue LocalDateTime,
    val finishDate: @RawValue LocalDateTime,
) : Parcelable
