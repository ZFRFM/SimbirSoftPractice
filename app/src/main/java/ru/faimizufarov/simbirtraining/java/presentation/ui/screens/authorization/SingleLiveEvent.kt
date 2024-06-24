package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val pending = AtomicBoolean(false)

    override fun observe(
        owner: LifecycleOwner,
        observer: Observer<in T>,
    ) {
        super.observe(owner) { event ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(event)
            }
        }
    }

    override fun setValue(t: T?) {
        pending.set(true)
        super.setValue(t)
    }

    fun call() {
        value = null
    }
}
