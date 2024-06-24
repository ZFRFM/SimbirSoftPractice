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

        authorizationViewModel.isAuthEnabled.observe(this) { isEnabled ->
            contentBinding.buttonSignIn.isEnabled = isEnabled
        }

        setAuthData()

        authorizationViewModel.navigateToActivity.observe(this) { destination ->
            destination?.let {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                authorizationViewModel.navigationHandled()
            }
        }

        contentBinding.buttonSignIn.setOnClickListener {
            authorizationViewModel.navigateTo(MainActivity::class.java)
        }

        contentBinding.vk.setOnClickListener {
            authorizationViewModel.navigateTo(MainActivity::class.java)
        }

        authorizationViewModel.finishActivity.observe(this) {
            finish()
        }

        binding.imageViewBack.setOnClickListener {
            authorizationViewModel.onFinishActivity()
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
