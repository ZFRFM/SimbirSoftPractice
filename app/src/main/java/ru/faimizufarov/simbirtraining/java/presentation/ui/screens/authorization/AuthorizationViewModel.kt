package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

const val TEXT_LENGTH = 6

class AuthorizationViewModel : ViewModel() {
    private val emailLiveData = MutableLiveData<String>()
    private val passwordLiveData = MutableLiveData<String>()

    private val _isAuthEnabledLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isAuthEnabledLiveData: LiveData<Boolean> = _isAuthEnabledLiveData

    init {
        emailLiveData.observeForever { _ ->
            checkIsValidValue()
        }
        passwordLiveData.observeForever { _ ->
            checkIsValidValue()
        }
    }

    private fun checkIsValidValue() {
        val isEmailValid =
            emailLiveData.value?.let {
                it.length >= TEXT_LENGTH
            } ?: false

        val isPasswordValid =
            passwordLiveData.value?.let {
                it.length >= TEXT_LENGTH
            } ?: false

        this._isAuthEnabledLiveData.value = isEmailValid && isPasswordValid
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
        emailLiveData.value = emailText
    }

    fun setPasswordText(passwordText: String) {
        passwordLiveData.value = passwordText
    }
}
