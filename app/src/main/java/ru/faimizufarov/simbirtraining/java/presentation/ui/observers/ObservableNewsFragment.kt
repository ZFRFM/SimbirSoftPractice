package ru.faimizufarov.simbirtraining.java.presentation.ui.observers

import ru.faimizufarov.simbirtraining.java.presentation.ui.activities.MainActivity

interface ObservableNewsFragment {
    val observer: MainActivity?

    fun sendUpdateBadgeCountEvent(unreadNewsCount: Int) {
        observer?.update(unreadNewsCount) ?: error("observer is null")
    }
}
