package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.main.MainActivity
import ru.faimizufarov.core.theme.HelpTheme

class AuthorizationActivity : AppCompatActivity() {
    private val authorizationViewModel: AuthorizationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelpTheme {
                AuthorizationScreen(
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
        with(authorizationViewModel) {
            navigateToMainLiveEvent.observe(this@AuthorizationActivity) {
                navigateToMainActivityLocal()
            }

            finishAuthorizationLiveEvent.observe(this@AuthorizationActivity) {
                finish()
            }
        }
    }

    private fun navigateToMainActivityLocal() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
