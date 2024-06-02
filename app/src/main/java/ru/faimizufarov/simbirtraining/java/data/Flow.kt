package ru.faimizufarov.simbirtraining.java.data

import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.rx3.asObservable

fun <T : Any> Flow<T>.toObservable(): Observable<T> {
    return this.asObservable()
}
