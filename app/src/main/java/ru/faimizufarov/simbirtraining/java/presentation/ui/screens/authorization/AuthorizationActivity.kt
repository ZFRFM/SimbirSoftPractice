package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.faimizufarov.simbirtraining.databinding.ActivityAuthorizationBinding
import ru.faimizufarov.simbirtraining.databinding.ScrollingAuthorizationActivityBinding
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.main.MainActivity

class AuthorizationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthorizationBinding
    private val contentBinding: ScrollingAuthorizationActivityBinding
        get() = binding.contentAuthorizationActivity

    private val disposables = CompositeDisposable()
    private val authorizationViewModel: AuthorizationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAuthData()
        observeViewModel()

        contentBinding.buttonSignIn.setOnClickListener {
            authorizationViewModel.navigateToMainActivity()
        }

        contentBinding.vk.setOnClickListener {
            authorizationViewModel.navigateToMainActivity()
        }

        binding.imageViewBack.setOnClickListener {
            authorizationViewModel.finishAuthorizationActivity()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    private fun observeViewModel() =
        with(authorizationViewModel) {
            isAuthEnabledLiveData.observe(this@AuthorizationActivity) { isEnabled ->
                contentBinding.buttonSignIn.isEnabled = isEnabled
            }

            emailLiveData.observe(this@AuthorizationActivity) { emailText ->
                if (contentBinding.editTextEmail.text.toString() != emailText) {
                    contentBinding.editTextEmail.setText(emailText)
                }
            }

            passwordLiveData.observe(this@AuthorizationActivity) { passwordText ->
                if (contentBinding.editTextPassword.text?.toString() != passwordText) {
                    contentBinding.editTextPassword.setText(passwordText)
                }
            }

            navigateToMainActivity.observe(this@AuthorizationActivity) { event ->
                event.getContentIfNotHandled()?.let { command ->
                    when (command) {
                        is NavigationCommand.ToMainActivity -> {
                            navigateToMainActivityLocal()
                        }
                        else -> {
                            return@let
                        }
                    }
                }
            }

            finishAuthorizationActivity.observe(this@AuthorizationActivity) { event ->
                event.getContentIfNotHandled()?.let { command ->
                    when (command) {
                        is NavigationCommand.FinishActivity -> {
                            finishActivity()
                        }
                        else -> {
                            return@let
                        }
                    }
                }
            }
        }

    private fun navigateToMainActivityLocal() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private fun finishActivity() {
        finish()
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
