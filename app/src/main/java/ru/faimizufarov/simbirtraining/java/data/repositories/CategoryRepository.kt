package ru.faimizufarov.simbirtraining.java.data.repositories

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.serialization.json.Json
import ru.faimizufarov.simbirtraining.java.data.models.Category
import ru.faimizufarov.simbirtraining.java.data.models.CategoryAsset
import ru.faimizufarov.simbirtraining.java.data.models.CategoryResponse
import ru.faimizufarov.simbirtraining.java.network.AppApi
import java.io.BufferedReader
import java.util.Locale

class CategoryRepository(
    private val context: Context,
) {
    private val api = AppApi.retrofitService
    private val assetManager = context.assets

    fun getCategoriesObservable() =
        getCategoriesFromApi().onErrorResumeNext {
            getCategoriesFromAssets()
        }

    private fun getCategoriesFromApi() =
        api.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { responses ->
                val observables =
                    responses
                        .map { response -> response.toCategorySingle() }
                        .map { categorySingle -> categorySingle.toObservable() }
                Observable.combineLatest(observables) { observablesArray ->
                    observablesArray.toList().filterIsInstance<Category>()
                }
            }

    private fun getCategoriesFromAssets() =
        Observable.create { emitter ->
            val assetsReader =
                assetManager
                    .open("responses/categories_list.json")
                    .bufferedReader()
            val categoriesJson = assetsReader.use(BufferedReader::readText)
            emitter.onNext(categoriesJson)
        }
            .map<Array<CategoryAsset>>(Json.Default::decodeFromString)
            .map { categoryResponseArray ->
                categoryResponseArray.map { categoryResponse ->
                    categoryResponse.toCategory(assetManager)
                }
            }

    private fun CategoryResponse.toCategorySingle() =
        Single.create { emitter ->
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
                            emitter.onSuccess(resource)
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {}
                    },
                )
        }
            .map { bitmap ->
                val locale = context.resources.configuration.locales.get(0)
                val isLocal = locale == Locale.forLanguageTag("ru")
                val title = if (isLocal) this.localizedName else this.globalName
                Category(
                    id = id,
                    title = title,
                    image = bitmap,
                )
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    private fun CategoryAsset.toCategory(assetManager: AssetManager) =
        Category(
            id = id,
            title = localizedName,
            image = BitmapFactory.decodeStream(assetManager.open(imagePath)),
        )
}
