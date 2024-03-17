package ru.faimizufarov.simbirtraining.java.data

import ru.faimizufarov.simbirtraining.R

enum class HelpCategoryEnum(val imageView: Int, val nameCategory: Int) {
    CHILDREN(imageView = R.drawable.children_category, nameCategory = R.string.children),
    ADULTS(imageView = R.drawable.adults_category, nameCategory = R.string.adults),
    ELDERLY(imageView = R.drawable.elderly_category, nameCategory = R.string.elderly),
    ANIMALS(imageView = R.drawable.animals_category, nameCategory = R.string.animals),
    EVENTS(imageView = R.drawable.events_category, nameCategory = R.string.events),
}
