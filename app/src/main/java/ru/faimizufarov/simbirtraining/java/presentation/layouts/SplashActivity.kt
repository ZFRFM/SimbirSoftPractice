package ru.faimizufarov.simbirtraining.java.presentation.layouts

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import ru.faimizufarov.simbirtraining.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }, 1250)
    }
}
