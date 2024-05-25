package ru.faimizufarov.simbirtraining.java.data.models

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize
import ru.faimizufarov.simbirtraining.R

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

@Deprecated("Horrible, but temporary")
fun String.categoryEnumFromId() =
    when (this) {
        "0" -> HelpCategoryEnum.CHILDREN
        "1" -> HelpCategoryEnum.ADULTS
        "2" -> HelpCategoryEnum.ELDERLY
        "3" -> HelpCategoryEnum.ANIMALS
        "4" -> HelpCategoryEnum.EVENTS
        else -> error("Unknown category")
    }

@StringRes
@Deprecated("Horrible, but temporary")
fun HelpCategoryEnum.toTitleRes() =
    when (this) {
        HelpCategoryEnum.CHILDREN -> R.string.children
        HelpCategoryEnum.ADULTS -> R.string.adults
        HelpCategoryEnum.ELDERLY -> R.string.elderly
        HelpCategoryEnum.ANIMALS -> R.string.animals
        HelpCategoryEnum.EVENTS -> R.string.events
    }
