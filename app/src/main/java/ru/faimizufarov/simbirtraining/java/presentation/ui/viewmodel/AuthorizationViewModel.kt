package ru.faimizufarov.simbirtraining.java.presentation.ui.viewmodel

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
