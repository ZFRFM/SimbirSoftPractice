package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

const val TEXT_LENGTH = 6

class AuthorizationViewModel : ViewModel() {
    private val _emailText = MutableLiveData<String>()
    val emailText: LiveData<String> = _emailText

    private val _passwordText = MutableLiveData<String>()
    val passwordText: LiveData<String> = _passwordText

    val isAuthEnabled =
        MediatorLiveData<Boolean>()
            .apply {
                addSource(emailText) { email ->
                    val password = passwordText.value
                    if (password != null) {
                        value =
                            email.length >= TEXT_LENGTH && password.length >= TEXT_LENGTH
                    }
                }
                addSource(passwordText) { password ->
                    val email = emailText.value
                    if (email != null) {
                        value =
                            email.length >= TEXT_LENGTH && password.length >= TEXT_LENGTH
                    }
                }
            }

    private val _navigateToActivity = SingleLiveEvent<Class<out AppCompatActivity>?>()
    val navigateToActivity: LiveData<Class<out AppCompatActivity>?> = _navigateToActivity

    private val _finishActivity = SingleLiveEvent<Unit>()
    val finishActivity: LiveData<Unit> = _finishActivity

    fun navigateTo(destination: Class<out AppCompatActivity>) {
        _navigateToActivity.value = destination
    }

    fun navigationHandled() {
        _navigateToActivity.value = null
    }

    fun onFinishActivity() {
        _finishActivity.call()
    }

    fun setEmailText(emailText: String) {
        _emailText.value = emailText
    }

    fun setPasswordText(passwordText: String) {
        _passwordText.value = passwordText
    }
}
