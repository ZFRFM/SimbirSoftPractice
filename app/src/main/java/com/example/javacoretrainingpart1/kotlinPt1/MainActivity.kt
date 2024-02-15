package com.example.javacoretrainingpart1.kotlinPt1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.javacoretrainingpart1.R
import java.util.Currency

const val TAG = "PublicationTag"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val euro = Currency.getInstance("EUR").symbol
        val book1 = Book(price = 400, wordCount = 3420)
        val book2 = Book(price = 650, wordCount = 7783)

        val book3: Book? = null
        val book4: Book? = Book(500, 5406)

        val magazine1 = Magazine(price = 200, 1027)

        val sum = {x: Int, y: Int ->
            Log.d(TAG, (x + y).toString())
        }

        sum(88, 23)
        sum(4637, 2304)
        sum(12345, 234)

        Log.d(TAG, "${book1.getType()}; ${book1.price} $euro; ${book1.wordCount}")
        Log.d(TAG, "${book2.getType()}; ${book2.price} $euro; ${book2.wordCount}")
        Log.d(TAG, "${magazine1.getType()}; ${magazine1.price} $euro; ${magazine1.wordCount}")
        Log.d(TAG, "${book1 === book2}")
        Log.d(TAG, "${book1.equals(book2)}")


        book3.let {
            if (it != null) {
                buy(it)
            }
        }
        book4.let {
            if (it != null) {
                buy(it)
            }
        }
    }

    fun buy(publication: Publication) {
        Log.d(TAG, "The purchase is complete. The purchase amount was ${publication.price}")
    }
}