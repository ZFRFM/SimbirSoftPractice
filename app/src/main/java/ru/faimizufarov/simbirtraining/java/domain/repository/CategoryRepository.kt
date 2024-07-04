package ru.faimizufarov.simbirtraining.java.domain.repository

import ru.faimizufarov.simbirtraining.java.domain.models.Category

interface CategoryRepository {
    suspend fun getCategoryList(): List<Category>
}
