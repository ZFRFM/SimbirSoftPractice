package ru.faimizufarov.simbirtraining.java.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.faimizufarov.simbirtraining.R

@Parcelize
enum class HelpCategoryEnum(
    val id: Int,
    val imageView: Int,
    val nameCategory: Int,
) : Parcelable {
    CHILDREN(0, imageView = R.drawable.children_category, nameCategory = R.string.children),
    ADULTS(1, imageView = R.drawable.adults_category, nameCategory = R.string.adults),
    ELDERLY(2, imageView = R.drawable.elderly_category, nameCategory = R.string.elderly),
    ANIMALS(3, imageView = R.drawable.animals_category, nameCategory = R.string.animals),
    EVENTS(4, imageView = R.drawable.events_category, nameCategory = R.string.events),
}
