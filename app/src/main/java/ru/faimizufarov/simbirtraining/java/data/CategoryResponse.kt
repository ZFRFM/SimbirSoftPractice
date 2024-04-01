package ru.faimizufarov.simbirtraining.java.data

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    val id: Int,
    val checked: Boolean,
)
