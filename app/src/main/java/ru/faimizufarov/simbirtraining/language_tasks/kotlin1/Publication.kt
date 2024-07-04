package ru.faimizufarov.simbirtraining.language_tasks.kotlin1

import java.util.Currency

interface Publication {
    val price: Int
    val wordCount: Int

    fun getType(): String
}

fun Publication.returnInfoAboutPublication(): String {
    val euro = Currency.getInstance("EUR").symbol
    return "${this.getType()}; ${this.price} $euro; ${this.wordCount}"
}
