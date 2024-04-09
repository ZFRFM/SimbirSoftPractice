package ru.faimizufarov.simbirtraining.java.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlinx.serialization.json.Json
import ru.faimizufarov.simbirtraining.java.data.CategoryResponse
import ru.faimizufarov.simbirtraining.java.data.HelpCategoryEnum

class JsonLoaderService : Service() {
    private val binder = LocalBinder()

    private var fileInString = ""
    private var listOfCategories = listOf<HelpCategoryEnum>()
    var onListOfCategoryChanged: ((List<HelpCategoryEnum>) -> Unit)? = null

    inner class LocalBinder : Binder() {
        fun getService(): JsonLoaderService = this@JsonLoaderService
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
                    this@JsonLoaderService
                        .applicationContext
                        .assets
                        .open("categories_list.json")
                        .bufferedReader()
                        .use { it.readText() }
                listOfCategories = convertInListOfCategories(fileInString)
                onListOfCategoryChanged?.invoke(listOfCategories)
            }
        workThread.start()
        return super.onStartCommand(intent, flags, startId)
    }

    fun getListOfCategories(): List<HelpCategoryEnum> {
        return this.listOfCategories
    }

    private fun convertInListOfCategories(json: String): List<HelpCategoryEnum> {
        return Json
            .decodeFromString<Array<CategoryResponse>>(json).map {
                when (it.id) {
                    0 -> HelpCategoryEnum.CHILDREN
                    1 -> HelpCategoryEnum.ADULTS
                    2 -> HelpCategoryEnum.ELDERLY
                    3 -> HelpCategoryEnum.ANIMALS
                    else -> HelpCategoryEnum.EVENTS
                }
            }
    }
}
