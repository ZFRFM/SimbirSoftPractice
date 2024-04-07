package ru.faimizufarov.simbirtraining.java.data

import kotlinx.serialization.Serializable

@Serializable
data class HelpCategoryEnumResponse(
    val id: Int,
    val checked: Boolean,
)
