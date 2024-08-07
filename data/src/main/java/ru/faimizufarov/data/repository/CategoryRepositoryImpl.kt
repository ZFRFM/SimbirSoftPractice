package ru.faimizufarov.data.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import ru.faimizufarov.data.local.AppDatabase
import ru.faimizufarov.data.local.CategoryEntity
import ru.faimizufarov.data.models.CategoryAsset
import ru.faimizufarov.data.models.CategoryResponse
import ru.faimizufarov.data.network.AppApi
import ru.faimizufarov.domain.models.Category
import ru.faimizufarov.domain.repository.CategoryRepository
import java.io.BufferedReader
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CategoryRepositoryImpl(
    private val context: Context,
) : CategoryRepository {
    private val api = AppApi.retrofitService
    private val assetManager = context.assets
    private val database = AppDatabase.getDatabase(context)

    override suspend fun getCategoryList() =
        withContext(Dispatchers.IO) {
            try {
                withTimeout(2500) {
                    getCategoriesFromApi()
                }
            } catch (httpException: HttpException) {
                getCategoriesFromAssets()
            } catch (timeoutCancellationException: TimeoutCancellationException) {
                getCategoriesFromAssets()
            }
        }

    private suspend fun getCategoriesFromApi() =
        if (isCategoriesCached()) {
            loadCategoriesFromDatabase()
        } else {
            loadCategoriesFromNetwork()
        }

    private suspend fun getCategoriesFromAssets() =
        suspendCoroutine { continuation ->
            val assetsReader =
                assetManager
                    .open("responses/categories_list.json")
                    .bufferedReader()
            val categoryJson = assetsReader.use(BufferedReader::readText)
            val categoryAssets =
                Json.decodeFromString<Array<CategoryAsset>>(categoryJson)
            val categories =
                categoryAssets.toList().map { categoryAsset ->
                    categoryAsset.toCategory()
                }
            continuation.resume(categories)
        }

    private fun CategoryAsset.toCategory() =
        Category(
            id = id,
            title = localizedName,
            imagePath = imagePath,
        )

    private suspend fun loadCategoriesFromNetwork(): List<Category> {
        val categories = api.getCategories().map { response -> response.toCategory() }
        database.categoryDao().insertCategories(
            categories
                .map { category -> category.toCategoryEntity() },
        )
        return categories
    }

    private fun CategoryResponse.toCategory(): Category {
        val locale = context.resources.configuration.locales.get(0)
        val isLocal = locale == Locale.forLanguageTag("ru")
        val title = if (isLocal) this.localizedName else this.globalName
        return Category(
            id = id,
            title = title,
            imagePath = imageUrl,
        )
    }

    private suspend fun loadCategoriesFromDatabase(): List<Category> {
        val categories = database.categoryDao().getAllCategories()
        return categories.map { entity ->
            entity.toCategory()
        }
    }

    private fun Category.toCategoryEntity() =
        CategoryEntity(
            id = id,
            title = title,
            imagePath = imagePath,
        )

    private fun CategoryEntity.toCategory() =
        Category(
            id = id,
            title = title,
            imagePath = imagePath,
        )

    private suspend fun isCategoriesCached() = database.categoryDao().checkCategoriesCount() != 0
}
