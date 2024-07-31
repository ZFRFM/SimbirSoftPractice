package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.main.MainActivity

class AuthorizationActivity : AppCompatActivity() {
    private val authorizationViewModel: AuthorizationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AuthorizationScreen(
                    authorizationViewModel,
                    login = { authorizationViewModel.navigateToMainActivity() },
                    onBack = { authorizationViewModel.finishAuthorizationActivity() },
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
