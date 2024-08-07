package ru.faimizufarov.simbirtraining.java.presentation.services

import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Binder
import android.os.IBinder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.faimizufarov.domain.models.Category
import ru.faimizufarov.domain.usecase.GetCategoriesUseCase
import ru.faimizufarov.simbirtraining.java.data.repository.CategoryRepositoryImpl
import ru.faimizufarov.simbirtraining.java.presentation.models.CategoryPresentation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CategoryLoaderService : Service() {
    private val binder = LocalBinder()
    private val assetManager by lazy {
        applicationContext.assets
    }

    private val categoryRepositoryImpl by lazy {
        CategoryRepositoryImpl(applicationContext)
    }

    private val getCategoriesUseCase: GetCategoriesUseCase by lazy {
        GetCategoriesUseCase(categoryRepositoryImpl)
    }

    private var coroutineScope: CoroutineScope? = null

    private var onListOfCategoryChanged: ((List<CategoryPresentation>) -> Unit)? = null

    inner class LocalBinder : Binder() {
        fun getService(): CategoryLoaderService = this@CategoryLoaderService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        coroutineScope = CoroutineScope(Dispatchers.IO)
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        coroutineScope?.launch {
            delay(500)
            val categoryList =
                getCategoriesUseCase.execute()
                    .map { category -> category.toCategoryPresentation() }
            onListOfCategoryChanged?.invoke(categoryList)
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope?.cancel()
    }

    fun setOnListOfCategoryChangedListener(listener: (List<CategoryPresentation>) -> Unit) {
        onListOfCategoryChanged = listener
    }

    private suspend fun loadImage(imagePath: String) =
        if (imagePath.startsWith("http")) {
            suspendCoroutine { continuation ->
                Glide
                    .with(applicationContext)
                    .asBitmap()
                    .load(imagePath)
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
        } else {
            BitmapFactory.decodeStream(assetManager.open(imagePath))
        }

    private suspend fun Category.toCategoryPresentation() =
        CategoryPresentation(
            id = id,
            title = title,
            image = loadImage(imagePath),
        )
}
