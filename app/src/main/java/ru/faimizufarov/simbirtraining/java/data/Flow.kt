package ru.faimizufarov.simbirtraining.java.data

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asPublisher

fun <T : Any> Flow<T>.toObservable(): Observable<T> {
    val publisher = this.asPublisher()
    return Flowable.fromPublisher(publisher).toObservable()
}
