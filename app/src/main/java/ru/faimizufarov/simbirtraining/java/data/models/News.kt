package ru.faimizufarov.simbirtraining.java.data.models

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
    val helpCategoryFilter: List<HelpCategoryEnum>,
    val startDate: @RawValue LocalDateTime,
    val finishDate: @RawValue LocalDateTime,
) : Parcelable
