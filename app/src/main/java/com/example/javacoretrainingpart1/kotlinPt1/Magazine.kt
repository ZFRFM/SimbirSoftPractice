package com.example.javacoretrainingpart1.kotlinPt1

class Magazine(override val price: Int, override val wordCount: Int) : Publication {
    override fun getType(): String {
        return "Magazine"
    }
}