package ru.faimizufarov.domain.repository

interface CategoryRepository {
    suspend fun getCategoryList(): List<Category>
}
