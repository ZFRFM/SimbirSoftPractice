package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.faimizufarov.simbirtraining.java.presentation.ui.holders.BadgeCounterHolder

class MainViewModel : ViewModel() {
    private val _badgeCount = MutableLiveData<Int>()
    val badgeCount: LiveData<Int>
        get() = _badgeCount

    init {
        updateBadgeCount()
    }

    private fun updateBadgeCount() {
        viewModelScope.launch {
            BadgeCounterHolder.badgeCounter.collect {
                _badgeCount.value = it
            }
        }
    }
}
