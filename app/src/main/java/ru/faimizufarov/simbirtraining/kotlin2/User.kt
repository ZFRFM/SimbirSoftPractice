package ru.faimizufarov.simbirtraining.kotlin2

import android.util.Log
import java.util.Calendar

data class User(
    val id: Int,
    val name: String,
    val age: Int,
    val type: Type
) {
    val startTime by lazy {
        Calendar.getInstance()
    }
}

fun User.isAdult(): Boolean {
    if (age > 18) {
        Log.d(TAG, this.toString())
        return true
    }
    else {
        throw Error("This man is 18 years old or younger")
    }
}