package ru.faimizufarov.simbirtraining.java.presentation.ui.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.faimizufarov.simbirtraining.databinding.ActivityAuthorizationBinding
import ru.faimizufarov.simbirtraining.java.presentation.ui.viewmodel.AuthorizationViewModel

class AuthorizationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthorizationBinding
    private val disposables = CompositeDisposable()
    private val authorizationViewModel: AuthorizationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authorizationViewModel.isEnable.observe(this) {
            binding.contentAuthorizationActivity.buttonSignIn.isEnabled =
                authorizationViewModel.isEnable.value ?: false
        }

        binding.contentAuthorizationActivity.editTextEmail
            .setText(authorizationViewModel.emailText.value)

        binding.contentAuthorizationActivity.editTextPassword
            .setText(authorizationViewModel.passwordText.value)

        setAuthData()

        binding.imageViewBack.setOnClickListener {
            this.finish()
        }

        binding.contentAuthorizationActivity.buttonSignIn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.contentAuthorizationActivity.vk.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    private fun setAuthData() {
        binding
            .contentAuthorizationActivity
            .editTextEmail
            .textChanges()
            .subscribe {
                authorizationViewModel.setEmailText(it.toString())
            }.let { disposables.add(it) }

        binding
            .contentAuthorizationActivity
            .textInputLayoutPassword
            .editText
            ?.textChanges()
            ?.subscribe {
                authorizationViewModel.setPasswordText(it.toString())
            }.let { disposables.add(it ?: Observable.just(false).subscribe()) }
    }
}
