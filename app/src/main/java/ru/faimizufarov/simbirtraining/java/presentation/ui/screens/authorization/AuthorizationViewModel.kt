package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

const val TEXT_LENGTH = 6

class AuthorizationViewModel : ViewModel() {
    private val _emailLiveData = MutableLiveData<String>()
    val emailLiveData: LiveData<String> = _emailLiveData

    private val _passwordLiveData = MutableLiveData<String>()
    val passwordLiveData: LiveData<String> = _passwordLiveData

    val isAuthEnabledLiveData: LiveData<Boolean> =
        MediatorLiveData<Boolean>()
            .apply {
                addSource(emailLiveData) { email ->
                    val password = passwordLiveData.value ?: return@addSource
                    value = email.length >= TEXT_LENGTH && password.length >= TEXT_LENGTH
                }
                addSource(passwordLiveData) { password ->
                    val email = emailLiveData.value ?: return@addSource
                    value = password.length >= TEXT_LENGTH && email.length >= TEXT_LENGTH
                }
            }

    private val _navigateToMainLiveEvent = SingleLiveEvent<Unit>()
    val navigateToMainLiveEvent: LiveData<Unit> = _navigateToMainLiveEvent

    private val _finishAuthorizationLiveEvent = SingleLiveEvent<Unit>()
    val finishAuthorizationLiveEvent: LiveData<Unit> = _finishAuthorizationLiveEvent

    fun navigateToMainActivity() {
        _navigateToMainLiveEvent.postValue(Unit)
    }

    fun finishAuthorizationActivity() {
        _finishAuthorizationLiveEvent.postValue(Unit)
    }

    fun setEmailText(emailText: String) {
        _emailLiveData.value = emailText
    }

    fun setPasswordText(passwordText: String) {
        _passwordLiveData.value = passwordText
    }
}
