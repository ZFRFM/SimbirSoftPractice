package ru.faimizufarov.simbirtraining.java.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val enumValue: HelpCategoryEnum?,
    var checked: Boolean = true,
) : Parcelable
