package ru.faimizufarov.simbirtraining.java.data

import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

fun <T : Any> Observable<T>.toFlow() =
    channelFlow {
        val disposable =
            subscribe { element ->
                this.launch {
                    send(element)
                }
            }
        awaitClose {
            disposable.dispose()
        }
    }
