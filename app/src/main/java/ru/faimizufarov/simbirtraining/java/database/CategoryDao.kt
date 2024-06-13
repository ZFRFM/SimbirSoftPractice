package ru.faimizufarov.simbirtraining.java.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category WHERE id= :id")
    suspend fun getCategoryById(id: String): CategoryEntity

    @Query("SELECT * FROM  category")
    suspend fun getAllCategories(): List<CategoryEntity>

    @Insert
    suspend fun insertCategories(categories: List<CategoryEntity>)

    @Query("SELECT COUNT(*) FROM category")
    suspend fun checkCategoriesCount(): Int
}
