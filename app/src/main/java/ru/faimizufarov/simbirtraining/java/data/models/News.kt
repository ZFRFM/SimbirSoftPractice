package ru.faimizufarov.simbirtraining.java.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class News(
    val id: String,
    val nameText: String,
    val startDate: Long,
    val finishDate: Long,
    val descriptionText: String,
    val status: Long,
    val newsImages: List<String>,
    val categoryIds: @RawValue List<String>,
    val createAt: Long,
    val phone: String,
    val address: String,
    val organisation: String,
) : Parcelable
