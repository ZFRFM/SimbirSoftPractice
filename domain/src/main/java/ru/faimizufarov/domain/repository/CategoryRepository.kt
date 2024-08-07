package ru.faimizufarov.domain.repository

import ru.faimizufarov.domain.models.Category

interface CategoryRepository {
    suspend fun getCategoryList(): List<Category>
}
