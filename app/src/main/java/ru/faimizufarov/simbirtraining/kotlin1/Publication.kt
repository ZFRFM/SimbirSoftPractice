package ru.faimizufarov.simbirtraining.kotlin1

import java.util.Currency

interface Publication {
    val price: Int
    val wordCount: Int

    fun getType(): String
}

fun Publication.returnInfoAboutPublication(type: String, price: Int, wordCount: Int): String {
    val euro = Currency.getInstance("EUR").symbol
    return "$type; $price $euro; $wordCount"
}