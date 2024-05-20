package ru.faimizufarov.simbirtraining.java.data.models

import android.graphics.Bitmap

data class Category(
    val id: String,
    val globalName: String,
    val localizedName: String,
    val icon: Bitmap,
)
