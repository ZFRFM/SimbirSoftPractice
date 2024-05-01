package ru.faimizufarov.simbirtraining.java.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.serialization.json.Json
import ru.faimizufarov.simbirtraining.java.data.CategoryResponse
import ru.faimizufarov.simbirtraining.java.data.HelpCategoryEnum
import ru.faimizufarov.simbirtraining.java.data.mapToHelpCategoryEnum
import java.util.concurrent.TimeUnit

class CategoryLoaderService : Service() {
    private val binder = LocalBinder()

    private var listOfCategories: List<HelpCategoryEnum>? = null
    private var onListOfCategoryChanged: ((List<HelpCategoryEnum>) -> Unit)? = null

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
        receiveCategoryListJsonInString()

        return super.onStartCommand(intent, flags, startId)
    }

    private fun receiveCategoryListJsonInString() {
        val jsonObservable =
            Observable.create { emitter ->
                val categoryListJsonInString =
                    this@CategoryLoaderService
                        .applicationContext
                        .assets
                        .open("categories_list.json")
                        .bufferedReader()
                        .use { it.readText() }
                emitter.onNext(categoryListJsonInString)
            }
                .delay(5000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())

        jsonObservable.subscribe { categoryListJsonInString ->
            listOfCategories = convertToListOfCategories(categoryListJsonInString)
            onListOfCategoryChanged?.invoke(listOfCategories ?: listOf())
        }.let(disposables::add)
    }

    fun setOnListOfCategoryChangedListener(listener: (List<HelpCategoryEnum>) -> Unit) {
        onListOfCategoryChanged = listener
    }

    private fun convertToListOfCategories(json: String): List<HelpCategoryEnum> {
        return Json
            .decodeFromString<Array<CategoryResponse>>(json).map {
                it.mapToHelpCategoryEnum()
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}
