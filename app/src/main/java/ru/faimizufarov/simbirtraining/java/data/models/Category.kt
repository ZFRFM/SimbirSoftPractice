package ru.faimizufarov.simbirtraining.java.data.models

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: String,
    val title: String,
    val image: Bitmap?,
) : Parcelable
