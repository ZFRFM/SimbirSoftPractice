package ru.faimizufarov.simbirtraining.java.presentation.ui.holders

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object BadgeCounterHolder {
    private val _badgeCounter = MutableStateFlow(0)
    val badgeCounter: StateFlow<Int> = _badgeCounter

    suspend fun setBadgeCounterEmitValue(emitValue: Int) {
        _badgeCounter.emit(emitValue)
    }
}
