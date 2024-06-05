package ru.faimizufarov.simbirtraining.java.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.faimizufarov.simbirtraining.java.data.models.Category
import ru.faimizufarov.simbirtraining.java.data.repositories.CategoryRepository

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
        val coroutineScope = CoroutineScope(Dispatchers.IO)

        coroutineScope.launch {
            delay(500)
            val categoryList = categoryRepository.getCategoryList()
            onListOfCategoryChanged?.invoke(categoryList)
        }

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
