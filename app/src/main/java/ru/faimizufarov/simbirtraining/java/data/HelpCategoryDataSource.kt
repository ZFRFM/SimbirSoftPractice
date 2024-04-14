package ru.faimizufarov.simbirtraining.java.data

import ru.faimizufarov.simbirtraining.R

object HelpCategoryDataSource {
    val helpCategoryList =
        listOf(
            HelpCategory(imageView = R.drawable.children_category, textView = R.string.children),
            HelpCategory(imageView = R.drawable.adults_category, textView = R.string.adults),
            HelpCategory(imageView = R.drawable.elderly_category, textView = R.string.elderly),
            HelpCategory(imageView = R.drawable.animals_category, textView = R.string.animals),
            HelpCategory(imageView = R.drawable.events_category, textView = R.string.events),
        )
}
