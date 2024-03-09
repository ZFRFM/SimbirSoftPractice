package ru.faimizufarov.simbirtraining.java.data

import ru.faimizufarov.simbirtraining.R

class HelpCategoryList {
    val helpCategoryList =
        listOf(
            HelpCategory(R.drawable.children_category, R.string.children),
            HelpCategory(R.drawable.adults_category, R.string.adults),
            HelpCategory(R.drawable.elderly_category, R.string.elderly),
            HelpCategory(R.drawable.animal_category, R.string.animals),
            HelpCategory(R.drawable.events_category, R.string.events),
        )
}
