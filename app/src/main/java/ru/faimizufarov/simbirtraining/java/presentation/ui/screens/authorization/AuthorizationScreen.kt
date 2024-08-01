package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization.composeunit.AuthorizationScreenBase
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization.composeunit.AuthorizationTopAppBar

@Composable
fun AuthorizationScreen(modifier: Modifier = Modifier) {
    val authorizationViewModel: AuthorizationViewModel = viewModel()
    val isSignInButtonEnabledState = authorizationViewModel.isAuthEnabledLiveData.observeAsState()

    Scaffold(
        modifier =
            modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize(),
        topBar = {
            AuthorizationTopAppBar(
                backPressed = {
                    authorizationViewModel.finishAuthorizationActivity()
                },
            )
        },
    ) { innerPadding ->
        AuthorizationScreenBase(
            login = { authorizationViewModel.navigateToMainActivity() },
            isSignInButtonEnabled = isSignInButtonEnabledState.value ?: false,
            setEmailText = authorizationViewModel::setEmailText,
            setPasswordText = authorizationViewModel::setPasswordText,
            modifier = Modifier.padding(innerPadding),
        )
    }
}
