package ru.faimizufarov.simbirtraining.java.data.repositories

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import ru.faimizufarov.simbirtraining.java.data.models.Category
import ru.faimizufarov.simbirtraining.java.data.models.CategoryAsset
import ru.faimizufarov.simbirtraining.java.data.models.CategoryResponse
import ru.faimizufarov.simbirtraining.java.database.AppDatabase
import ru.faimizufarov.simbirtraining.java.database.CategoryEntity
import ru.faimizufarov.simbirtraining.java.network.AppApi
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CategoryRepository(
    private val context: Context,
) {
    private val api = AppApi.retrofitService
    private val assetManager = context.assets
    private val database = AppDatabase.getDatabase(context)

    suspend fun getCategoryList() =
        withContext(Dispatchers.IO) {
            try {
                withTimeout(5000) {
                    getCategoriesFromApi()
                }
            } catch (httpException: HttpException) {
                getCategoriesFromAssets()
            } catch (timeoutCancellationException: TimeoutCancellationException) {
                getCategoriesFromAssets()
            }
        }

    private suspend fun getCategoriesFromApi() = isCategoriesCached()

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
                    categoryAsset.toCategory(assetManager)
                }
            continuation.resume(categories)
        }

    private suspend fun CategoryResponse.toCategory(): Category {
        val bitmap = loadImage(this.imageUrl)
        val locale = context.resources.configuration.locales.get(0)
        val isLocal = locale == Locale.forLanguageTag("ru")
        val title = if (isLocal) this.localizedName else this.globalName
        return Category(
            id = id,
            title = title,
            image = bitmap,
        )
    }

    private fun CategoryAsset.toCategory(assetManager: AssetManager) =
        Category(
            id = id,
            title = localizedName,
            image = BitmapFactory.decodeStream(assetManager.open(imagePath)),
        )

    private suspend fun loadImage(imageUrl: String) =
        suspendCoroutine { continuation ->
            Glide
                .with(context)
                .asBitmap()
                .load(imageUrl)
                .into(
                    object : CustomTarget<Bitmap?>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap?>?,
                        ) {
                            continuation.resume(resource)
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {}

                        override fun onLoadFailed(errorDrawable: Drawable?) {
                            continuation.resumeWithException(
                                Exception("Bitmap loading failed"),
                            )
                        }
                    },
                )
        }

    private suspend fun loadCategoriesFromNetwork(): List<Category> {
        val categories = api.getCategories().map { response -> response.toCategory() }
        database.categoryDao().insertCategories(
            categories.map {
                it.toCategoryEntity(::saveBitmapInLocalStorage)
            },
        )
        return categories
    }

    private suspend fun Category.toCategoryEntity(saveImageInFile: suspend (String, Bitmap) -> Unit): CategoryEntity {
        val directory = context.getDir("bitmaps", Context.MODE_PRIVATE)
        val file = File(directory, "category_image_$id.png")
        return if (file.exists()) {
            CategoryEntity(
                id = id,
                title = title,
            )
        } else {
            saveImageInFile(id, image)
            CategoryEntity(
                id = id,
                title = title,
            )
        }
    }

    private fun saveBitmapInLocalStorage(
        imageId: String,
        bitmap: Bitmap,
    ) {
        val directory = context.getDir("bitmaps", Context.MODE_PRIVATE)
        val file = File(directory, "category_image_$imageId.png")
        val fileOutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        fileOutputStream.flush()
    }

    private suspend fun loadCategoriesFromDatabase(): List<Category> {
        val categories = database.categoryDao().getAllCategories()
        return categories.map { entity ->
            entity.toCategory(::retrieveBitmap)
        }
    }

    private suspend fun CategoryEntity.toCategory(getImageFromId: suspend (String) -> Bitmap) =
        Category(
            id = id,
            title = title,
            image = getImageFromId(id),
        )

    private fun retrieveBitmap(imageId: String): Bitmap {
        val directory = context.getDir("bitmaps", Context.MODE_PRIVATE)
        val file = File(directory, "category_image_$imageId.png")
        return BitmapFactory.decodeFile(file.absolutePath)
    }

    private suspend fun isCategoriesCached(): List<Category> {
        return if (database.categoryDao().checkCategoriesCount() == 0) {
            loadCategoriesFromNetwork()
        } else {
            loadCategoriesFromDatabase()
        }
    }
}
