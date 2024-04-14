package ru.faimizufarov.simbirtraining.java.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HelpCategory(
    val imageView: Int,
    val textView: Int,
) : Parcelable
