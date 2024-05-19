package ru.faimizufarov.simbirtraining.java.data.models

import android.graphics.Bitmap

data class Category(
    val id: Int,
    val icon: Bitmap,
    val localizedName: String,
    val globalName: String
)
