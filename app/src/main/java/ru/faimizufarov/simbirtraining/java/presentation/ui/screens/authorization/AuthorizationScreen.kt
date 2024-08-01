package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization.composeunit.AuthorizationScreenBase
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization.composeunit.AuthorizationTopAppBar
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.HelpTheme

@Composable
fun AuthorizationScreen(modifier: Modifier = Modifier) {
    val authorizationViewModel: AuthorizationViewModel = viewModel()
    val isSignInButtonEnabled by authorizationViewModel.isAuthEnabledLiveData.observeAsState()

    AuthorizationScreen(
        modifier = modifier,
        isSignInButtonEnabled = isSignInButtonEnabled ?: false,
        finish = authorizationViewModel::finishAuthorizationActivity,
        login = authorizationViewModel::navigateToMainActivity,
        setEmailText = authorizationViewModel::setEmailText,
        setPasswordText = authorizationViewModel::setPasswordText,
    )
}

@Composable
private fun AuthorizationScreen(
    isSignInButtonEnabled: Boolean,
    finish: () -> Unit,
    login: () -> Unit,
    setEmailText: (String) -> Unit,
    setPasswordText: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier =
            modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize(),
        topBar = {
            AuthorizationTopAppBar(
                backPressed = finish,
            )
        },
    ) { innerPadding ->
        AuthorizationScreenBase(
            login = login,
            isSignInButtonEnabled = isSignInButtonEnabled,
            setEmailText = setEmailText,
            setPasswordText = setPasswordText,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Preview
@Composable
private fun AuthorizationScreen_Preview() =
    HelpTheme {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Surface {
            AuthorizationScreen(
                isSignInButtonEnabled = email.isNotEmpty() && password.isNotEmpty(),
                finish = {},
                login = {},
                setEmailText = { email = it },
                setPasswordText = { password = it },
            )
        }
    }
