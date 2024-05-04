package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object BadgeCounter {
    private val _badgeCounter = MutableStateFlow(0)
    val badgeCounter: StateFlow<Int> = _badgeCounter

    suspend fun setBadgeCounterEmitValue(emitValue: Int) {
        _badgeCounter.emit(emitValue)
    }
}
