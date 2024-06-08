package ru.faimizufarov.simbirtraining.java.data.repositories

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
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
        try {
            withTimeout(5000) {
                getCategoriesFromApi()
            }
        } catch (httpException: HttpException) {
            getCategoriesFromAssets()
        } catch (timeoutCancellationException: TimeoutCancellationException) {
            getCategoriesFromAssets()
        }

    private suspend fun getCategoriesFromApi() =
        if (database.categoryDao().checkCategoriesCount() == 0) {
            loadCategoriesFromNetwork()
        } else {
            loadCategoriesFromDatabase()
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

    private suspend fun saveBitmapInLocalStorage(
        context: Context,
        bitmap: Bitmap,
        filename: String,
    ) = suspendCoroutine<String> { continuation ->
        val directory = context.getDir("bitmaps", Context.MODE_PRIVATE)
        val file = File(directory, "$filename.png")
        val fileOutputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        fileOutputStream.flush()
        continuation.resume(file.absolutePath)
    }

    private suspend fun retrieveBitmap(id: String) =
        suspendCoroutine<Bitmap> { continuation ->
            val coroutineScope = CoroutineScope(Dispatchers.IO)
            coroutineScope.launch {
                val categoryEntity = database.categoryDao().getCategoryById(id)
                categoryEntity.let {
                    val file = File(it.imagePath)
                    if (file.exists()) {
                        val bitmap = BitmapFactory.decodeFile(it.imagePath)
                        continuation.resume(bitmap)
                    }
                }
            }
            coroutineScope.cancel()
        }

    private suspend fun loadCategoriesFromNetwork(): List<Category> {
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        val categories = api.getCategories().map { response -> response.toCategory() }
        coroutineScope.launch {
            database.categoryDao().insertCategories(categories.map { it.toCategoryEntity() })
        }
        coroutineScope.cancel()
        return categories
    }

    private suspend fun loadCategoriesFromDatabase() =
        suspendCoroutine { continuation ->
            val coroutineScope = CoroutineScope(Dispatchers.IO)
            coroutineScope.launch {
                continuation.resume(
                    database.categoryDao().getAllCategories().map { it.toCategory() },
                )
            }
            coroutineScope.cancel()
        }

    private suspend fun Category.toCategoryEntity() =
        CategoryEntity(
            id = id,
            title = title,
            imagePath = saveBitmapInLocalStorage(context, image, id),
        )

    private suspend fun CategoryEntity.toCategory() =
        Category(
            id = id,
            title = title,
            image = retrieveBitmap(id),
        )
}
