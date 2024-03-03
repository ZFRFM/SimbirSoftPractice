package ru.faimizufarov.simbirtraining.kotlin.kotlin1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.faimizufarov.simbirtraining.R


const val TAG = "Publication Tag"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        val bookOne = Book(price = 400, wordCount = 3420)
        val bookTwo = Book(price = 650, wordCount = 7783)

        var bookThree: Book? = null
        var bookFour: Book? = Book(500, 5406)

        val magazineOne = Magazine(price = 200, 1027)

        val sum = {x: Int, y: Int ->
            Log.d(TAG, (x + y).toString())
        }

        sum(88, 23)
        sum(4637, 2304)
        sum(12345, 234)

        Log.d(TAG, bookOne.returnInfoAboutPublication())
        Log.d(TAG, bookTwo.returnInfoAboutPublication())
        Log.d(TAG, magazineOne.returnInfoAboutPublication())
        Log.d(TAG, "${bookOne === bookTwo}")
        Log.d(TAG, "${bookOne.equals(bookTwo)}")


        bookThree.let {
            if (it != null) {
                buy(it)
            }
        }
        bookFour.let {
            if (it != null) {
                buy(it)
            }
        }
    }

    fun buy(publication: Publication) {
        Log.d(TAG, "The purchase is complete. The purchase amount was ${publication.price}")
    }
}