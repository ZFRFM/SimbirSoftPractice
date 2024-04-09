package ru.faimizufarov.simbirtraining.java.data

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    val id: Int,
    val checked: Boolean,
)

fun CategoryResponse.mapToHelpCategoryEnum(): HelpCategoryEnum {
    return when (this.id) {
        0 -> HelpCategoryEnum.CHILDREN
        1 -> HelpCategoryEnum.ADULTS
        2 -> HelpCategoryEnum.ELDERLY
        3 -> HelpCategoryEnum.ANIMALS
        else -> HelpCategoryEnum.EVENTS
    }
}
