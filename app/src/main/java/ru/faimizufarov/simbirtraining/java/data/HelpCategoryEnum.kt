package ru.faimizufarov.simbirtraining.java.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class HelpCategoryEnum(
    val id: Int,
) : Parcelable {
    CHILDREN(0),
    ADULTS(1),
    ELDERLY(2),
    ANIMALS(3),
    EVENTS(4),
}
