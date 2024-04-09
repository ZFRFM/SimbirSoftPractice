package ru.faimizufarov.simbirtraining.java.services

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder

class CategoryLoaderServiceConnection(
    private val showCategories: Unit,
    private var categoryLoaderService: CategoryLoaderService,
) : ServiceConnection {
    override fun onServiceConnected(
        name: ComponentName?,
        service: IBinder?,
    ) {
        val binder = service as CategoryLoaderService.LocalBinder
        categoryLoaderService = binder.getService()

        categoryLoaderService.setOnListOfCategoryChangedListener { changedListOfCategory ->
            showCategories
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        categoryLoaderService.setOnListOfCategoryChangedListener {}
    }
}
