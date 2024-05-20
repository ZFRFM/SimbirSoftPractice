package ru.faimizufarov.simbirtraining.java.data.models

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    val id: Int,
    val checked: Boolean,
)

fun CategoryResponse.mapToHelpCategoryEnum() =
    HelpCategoryEnum
        .entries
        .firstOrNull { enum -> enum.id == id }
        ?: error("Unknown category")
