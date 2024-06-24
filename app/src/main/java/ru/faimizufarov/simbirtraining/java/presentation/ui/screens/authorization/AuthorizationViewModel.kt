package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthorizationViewModel : ViewModel() {
    private val _isEnable = MutableLiveData<Boolean>()
    val isEnable: LiveData<Boolean>
        get() = _isEnable

    private val _emailText = MutableLiveData<String>()
    val emailText: LiveData<String>
        get() = _emailText

    private val _passwordText = MutableLiveData<String>()
    val passwordText: LiveData<String>
        get() = _passwordText

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
        updateIsEnable()
    }

    fun setPasswordText(passwordText: String) {
        _passwordText.value = passwordText
        updateIsEnable()
    }

    private fun updateIsEnable() {
        _isEnable.value =
            (emailText.value?.length ?: 0) >= TEXT_LENGTH &&
            (passwordText.value?.length ?: 0) >= TEXT_LENGTH
    }

    companion object {
        const val TEXT_LENGTH = 6
    }
}
