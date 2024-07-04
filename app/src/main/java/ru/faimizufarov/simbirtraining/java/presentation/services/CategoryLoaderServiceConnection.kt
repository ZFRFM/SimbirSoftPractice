package ru.faimizufarov.simbirtraining.java.presentation.services

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import ru.faimizufarov.simbirtraining.java.domain.models.Category

class CategoryLoaderServiceConnection(
    private val showCategories: (List<Category>) -> Unit,
) : ServiceConnection {
    private var connectedService: CategoryLoaderService? = null

    override fun onServiceConnected(
        name: ComponentName?,
        service: IBinder?,
    ) {
        val binder = service as CategoryLoaderService.LocalBinder
        connectedService = binder.getService()

        connectedService?.setOnListOfCategoryChangedListener(showCategories)
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        connectedService?.setOnListOfCategoryChangedListener {
            // clearing callback
        }
        connectedService = null
    }
}
