package ru.faimizufarov.simbirtraining.java.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryFilter(
    val enumValue: HelpCategoryEnum?,
    // TODO: change to val
    var checked: Boolean = true,
) : Parcelable
