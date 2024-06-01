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
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.serialization.json.Json
import ru.faimizufarov.simbirtraining.java.data.models.Category
import ru.faimizufarov.simbirtraining.java.data.models.CategoryAsset
import ru.faimizufarov.simbirtraining.java.data.models.CategoryResponse
import ru.faimizufarov.simbirtraining.java.network.AppApi
import java.io.BufferedReader

class CategoryRepository(
    private val context: Context,
) {
    private val api = AppApi.retrofitService
    private val assetManager = context.assets

    private val disposables = CompositeDisposable()

    fun getCategoriesObservable() =
        getCategoriesFromApi().onErrorResumeNext {
            getCategoriesFromAssets()
        }

    private fun getCategoriesFromApi() =
        Observable.create { emitter ->
            try {
                api.getCategories()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        val categories = mutableListOf<Category>()
                        categories.addAll(
                            it.map { response -> response.toCategory() },
                        )
                        emitter.onNext(categories.toList())
                    }.let { disposables.add(it) }
            } catch (throwable: Throwable) {
                emitter.onError(throwable)
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

    private fun CategoryResponse.toCategory(): Category {
        var bitmap: Bitmap? = null
        Glide.with(context)
            .asBitmap()
            .load(this.imageUrl)
            .into(
                object : CustomTarget<Bitmap?>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap?>?,
                    ) {
                        bitmap = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) { }
                },
            )
        return Category(
            id = id,
            title = localizedName,
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
