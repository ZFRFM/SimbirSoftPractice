package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import ru.faimizufarov.simbirtraining.java.presentation.ui.holders.BadgeCounterHolder

class MainViewModel : ViewModel() {
    val badgeCount: LiveData<Int> =
        BadgeCounterHolder.badgeCounter
            .asLiveData(Dispatchers.IO)
}
