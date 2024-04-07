package ru.faimizufarov.simbirtraining.java.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class JsonLoaderService : Service() {
    private val binder = LocalBinder()

    var fileInString = ""
    var onFileInStringChanged: ((String) -> Unit)? = null

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
                onFileInStringChanged?.invoke(fileInString)
            }
        workThread.start()
        return super.onStartCommand(intent, flags, startId)
    }
}
