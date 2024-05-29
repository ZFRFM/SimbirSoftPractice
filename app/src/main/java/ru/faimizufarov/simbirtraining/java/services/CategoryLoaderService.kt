package ru.faimizufarov.simbirtraining.java.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.faimizufarov.simbirtraining.java.data.models.Category
import ru.faimizufarov.simbirtraining.java.data.repositories.CategoryRepository
import java.util.concurrent.TimeUnit

class CategoryLoaderService : Service() {
    private val binder = LocalBinder()

    private val categoryRepository by lazy {
        CategoryRepository(applicationContext)
    }

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
        categoryRepository.getCategoriesObservable()
            .delay(500, TimeUnit.MILLISECONDS)
            .subscribe {
                onListOfCategoryChanged?.invoke(it)
            }.let { disposables.add(it) }

        return super.onStartCommand(intent, flags, startId)
    }

    fun setOnListOfCategoryChangedListener(listener: (List<Category>) -> Unit) {
        onListOfCategoryChanged = listener
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}
