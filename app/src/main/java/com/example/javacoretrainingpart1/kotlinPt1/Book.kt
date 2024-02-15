package com.example.javacoretrainingpart1.kotlinPt1

class Book(override val price: Int, override val wordCount: Int) : Publication {
    override fun getType(): String {
        return if (wordCount in 0 until 1000) {
            "Flash Fiction"
        } else if (wordCount in 1000 until 7500) {
            "Short Story"
        } else if (wordCount > 7500) {
            "Novel"
        } else {
            "Negative number of pages? Really!"
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Book) return false

        if (price != other.price) return false
        return wordCount == other.wordCount
    }

}