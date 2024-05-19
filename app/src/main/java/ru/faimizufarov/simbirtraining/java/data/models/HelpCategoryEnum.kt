package ru.faimizufarov.simbirtraining.java.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Deprecated("Replace with Category")
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
