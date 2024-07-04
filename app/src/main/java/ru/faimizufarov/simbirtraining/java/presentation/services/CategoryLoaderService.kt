package ru.faimizufarov.simbirtraining.java.presentation.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.faimizufarov.simbirtraining.java.data.repository.CategoryRepositoryImpl
import ru.faimizufarov.simbirtraining.java.domain.models.Category
import ru.faimizufarov.simbirtraining.java.domain.usecase.GetCategoriesUseCase

class CategoryLoaderService : Service() {
    private val binder = LocalBinder()

    private val categoryRepositoryImpl by lazy {
        CategoryRepositoryImpl(applicationContext)
    }

    private val getCategoriesUseCase: GetCategoriesUseCase by lazy {
        GetCategoriesUseCase(categoryRepositoryImpl)
    }

    private var coroutineScope: CoroutineScope? = null

    private var onListOfCategoryChanged: ((List<Category>) -> Unit)? = null

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
            val categoryList = getCategoriesUseCase.execute()
            onListOfCategoryChanged?.invoke(categoryList)
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope?.cancel()
    }

    fun setOnListOfCategoryChangedListener(listener: (List<Category>) -> Unit) {
        onListOfCategoryChanged = listener
    }
}
