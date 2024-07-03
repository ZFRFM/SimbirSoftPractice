package ru.faimizufarov.simbirtraining.java.presentation.ui.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization.AuthorizationActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            startActivity(Intent(this, AuthorizationActivity::class.java))
            finish()
        }, 1000)
    }
}
