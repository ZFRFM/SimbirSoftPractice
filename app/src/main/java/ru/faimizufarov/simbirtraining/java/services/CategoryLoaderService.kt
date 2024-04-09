package ru.faimizufarov.simbirtraining.java.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlinx.serialization.json.Json
import ru.faimizufarov.simbirtraining.java.data.CategoryResponse
import ru.faimizufarov.simbirtraining.java.data.HelpCategoryEnum
import ru.faimizufarov.simbirtraining.java.data.mapToHelpCategoryEnum

class CategoryLoaderService : Service() {
    private val binder = LocalBinder()

    private var fileInString = ""
    private var listOfCategories = listOf<HelpCategoryEnum>()
    private var onListOfCategoryChanged: ((List<HelpCategoryEnum>) -> Unit)? = null

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
        val workThread =
            Thread {
                Thread.sleep(5000)
                fileInString =
                    this@CategoryLoaderService
                        .applicationContext
                        .assets
                        .open("categories_list.json")
                        .bufferedReader()
                        .use { it.readText() }
                listOfCategories = convertToListOfCategories(fileInString)
                onListOfCategoryChanged?.invoke(listOfCategories)
            }
        workThread.start()
        return super.onStartCommand(intent, flags, startId)
    }

    fun setOnListOfCategoryChangedListener(listener: ((List<HelpCategoryEnum>) -> Unit)?) {
        onListOfCategoryChanged = listener
    }

    fun getListOfCategories(): List<HelpCategoryEnum> {
        return this.listOfCategories
    }

    private fun convertToListOfCategories(json: String): List<HelpCategoryEnum> {
        return Json
            .decodeFromString<Array<CategoryResponse>>(json).map {
                it.mapToHelpCategoryEnum()
            }
    }
}
