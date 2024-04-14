package ru.faimizufarov.simbirtraining.java.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class HelpCategoryEnum(
    val id: Int,
    val categoryData: HelpCategory,
) : Parcelable {
    CHILDREN(0, HelpCategoryDataSource.helpCategoryList[0]),
    ADULTS(1, HelpCategoryDataSource.helpCategoryList[1]),
    ELDERLY(2, HelpCategoryDataSource.helpCategoryList[2]),
    ANIMALS(3, HelpCategoryDataSource.helpCategoryList[3]),
    EVENTS(4, HelpCategoryDataSource.helpCategoryList[4]),
}
