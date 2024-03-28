package ru.faimizufarov.simbirtraining.java.data

import kotlinx.serialization.Serializable

@Serializable
data class HelpCategoryEnumJsonRepresentation(
    val id: Int,
    val checked: Boolean,
)
