package ru.faimizufarov.simbirtraining.java.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.faimizufarov.simbirtraining.java.data.models.Category
import ru.faimizufarov.simbirtraining.java.data.models.mapToCategory
import ru.faimizufarov.simbirtraining.java.data.repositories.CategoryRepository
import ru.faimizufarov.simbirtraining.java.network.AppApi
import java.util.concurrent.TimeUnit

class CategoryLoaderService : Service() {
    private val binder = LocalBinder()

    private val categoryRepository by lazy {
        CategoryRepository(applicationContext.assets)
    }

    private var listOfCategories: List<Category>? = null
    private var onListOfCategoryChanged: ((List<Category>) -> Unit)? = null

    private val disposables = CompositeDisposable()

    inner class LocalBinder : Binder() {
        fun getService(): CategoryLoaderService = this@CategoryLoaderService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int,
    ): Int {
        val serviceCoroutineScope = CoroutineScope(Dispatchers.IO)

        val coroutineExceptionHandler =
            CoroutineExceptionHandler { _, _: Throwable ->
                receiveCategoryListJsonInString()
            }

        serviceCoroutineScope.launch(coroutineExceptionHandler) {
            listOfCategories =
                AppApi.retrofitService.getCategories().map {
                    it.mapToCategory()
                }
            onListOfCategoryChanged?.invoke(listOfCategories ?: emptyList())
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun receiveCategoryListJsonInString() {
        val jsonObservable =
            categoryRepository.getCategoriesObservable()
                .delay(2500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())

        jsonObservable.subscribe { categories ->
            listOfCategories = categories
            onListOfCategoryChanged?.invoke(listOfCategories ?: listOf())
        }.let(disposables::add)
    }

    fun setOnListOfCategoryChangedListener(listener: (List<Category>) -> Unit) {
        onListOfCategoryChanged = listener
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}
