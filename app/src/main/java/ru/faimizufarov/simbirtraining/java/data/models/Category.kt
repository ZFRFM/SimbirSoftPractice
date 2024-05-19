package ru.faimizufarov.simbirtraining.java.data.models

import android.graphics.Bitmap

data class Category(
    val id: String,
    val icon: Bitmap,
    val localizedName: String,
    val globalName: String
)

@Deprecated("Supposed to remove HelpCategoryEnum")
fun Category.toEnum() = when (id) {
    "0" -> HelpCategoryEnum.CHILDREN
    "1" -> HelpCategoryEnum.ADULTS
    "2" -> HelpCategoryEnum.ELDERLY
    "3" -> HelpCategoryEnum.ANIMALS
    "4" -> HelpCategoryEnum.EVENTS
    else -> error("Unknown category")
}
