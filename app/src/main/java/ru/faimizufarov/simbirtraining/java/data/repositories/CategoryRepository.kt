package ru.faimizufarov.simbirtraining.java.data.repositories

import android.content.res.AssetManager
import android.graphics.BitmapFactory
import io.reactivex.rxjava3.core.Observable
import kotlinx.serialization.json.Json
import ru.faimizufarov.simbirtraining.java.data.models.*
import java.io.BufferedReader
import java.io.IOException

class CategoryRepository(
    private val assetManager: AssetManager
) {
    fun getCategoriesObservable() = Observable.create { emitter ->
        val assetsReader = assetManager.open("responses/categories_list.json")
            .bufferedReader()
        val categoriesJson = assetsReader.use(BufferedReader::readText)
        emitter.onNext(categoriesJson)
    }
        .map<Array<CategoryResponse>>(Json.Default::decodeFromString)
        .map { categoryResponseArray ->
            categoryResponseArray.map { categoryResponse ->
                val iconStream = assetManager.open(categoryResponse.imagePath)
                val iconBitmap = BitmapFactory.decodeStream(iconStream)

                Category(
                    id = categoryResponse.id,
                    localizedName = categoryResponse.localizedName,
                    globalName = categoryResponse.globalName,
                    icon = iconBitmap
                )
            }
        }
}