package ru.faimizufarov.simbirtraining.java.data

import ru.faimizufarov.simbirtraining.R

enum class HelpCategoryEnum(val imageView: Int, name: Int) {
    CHILDREN(imageView = R.drawable.children_category, name = R.string.children),
    ADULTS(imageView = R.drawable.adults_category, name = R.string.adults),
    ELDERLY(imageView = R.drawable.elderly_category, name = R.string.elderly),
    ANIMALS(imageView = R.drawable.animals_category, name = R.string.animals),
    EVENTS(imageView = R.drawable.events_category, name = R.string.events),
}
