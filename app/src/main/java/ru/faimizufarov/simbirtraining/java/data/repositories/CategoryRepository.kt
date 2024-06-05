package ru.faimizufarov.simbirtraining.java.data.repositories

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.serialization.json.Json
import ru.faimizufarov.simbirtraining.java.data.models.Category
import ru.faimizufarov.simbirtraining.java.data.models.CategoryAsset
import ru.faimizufarov.simbirtraining.java.data.models.CategoryResponse
import ru.faimizufarov.simbirtraining.java.network.AppApi
import java.io.BufferedReader
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CategoryRepository(
    private val context: Context,
) {
    private val api = AppApi.retrofitService
    private val assetManager = context.assets

    suspend fun getCategoryList() =
        try {
            getCategoriesFromApi()
        } catch (exception: Exception) {
            getCategoriesFromAssets()
        }

    private suspend fun getCategoriesFromApi() = api.getCategories().map { response -> response.toCategory() }

    private fun getCategoriesFromAssets(): List<Category> {
        val assetsReader =
            assetManager
                .open("responses/categories_list.json")
                .bufferedReader()
        val categoryJson = assetsReader.use(BufferedReader::readText)
        val categoryAssets =
            Json.decodeFromString<Array<CategoryAsset>>(categoryJson)
        return categoryAssets.toList().map { categoryAsset ->
            categoryAsset.toCategory(assetManager)
        }
    }

    private suspend fun CategoryResponse.toCategory(): Category {
        val bitmap =
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
}
