package com.example.javacoretrainingpart1.kotlinPt1

interface Publication {
    val price: Int
    val wordCount: Int

    fun getType(): String
}