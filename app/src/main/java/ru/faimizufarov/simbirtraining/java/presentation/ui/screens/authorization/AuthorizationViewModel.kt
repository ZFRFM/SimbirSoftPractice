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

    private val _navigateToMainActivity = MutableLiveData<Event<NavigationCommand>>()
    val navigateToMainActivity: LiveData<Event<NavigationCommand>> = _navigateToMainActivity

    private val _finishAuthorizationActivity = MutableLiveData<Event<NavigationCommand>>()
    val finishAuthorizationActivity: LiveData<Event<NavigationCommand>> =
        _finishAuthorizationActivity

    fun navigateToMainActivity() {
        _navigateToMainActivity.value = Event(NavigationCommand.ToMainActivity)
    }

    fun finishAuthorizationActivity() {
        _finishAuthorizationActivity.value = Event(NavigationCommand.FinishActivity)
    }

    fun setEmailText(emailText: String) {
        _emailLiveData.value = emailText
    }

    fun setPasswordText(passwordText: String) {
        _passwordLiveData.value = passwordText
    }
}
