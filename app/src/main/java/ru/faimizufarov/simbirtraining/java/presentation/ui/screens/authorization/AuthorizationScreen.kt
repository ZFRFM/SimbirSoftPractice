package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization.composeunit.EmailTextField
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization.composeunit.HyperlinkText
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization.composeunit.PasswordTextField
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization.composeunit.TopAppBar

@Suppress("ktlint:standard:function-naming")
@Composable
fun AuthorizationScreen(
    authorizationViewModel: AuthorizationViewModel,
    modifier: Modifier = Modifier,
    login: () -> Unit,
    closeApp: () -> Unit,
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = modifier.fillMaxWidth(),
                backPressed = {
                    closeApp.invoke()
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier =
                modifier
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            Spacer(modifier = modifier.height(40.dp))

            Text(
                modifier = modifier.fillMaxWidth(),
                text = stringResource(R.string.authorization_top_text),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                color = colorResource(id = R.color.black_70),
            )

            Spacer(modifier = modifier.height(20.dp))

            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Image(painter = painterResource(R.drawable.vk), contentDescription = "")
                Image(painter = painterResource(R.drawable.fb), contentDescription = "")
                Image(painter = painterResource(R.drawable.ok), contentDescription = "")
            }

            Spacer(modifier = modifier.height(40.dp))

            Text(
                modifier = modifier.fillMaxWidth(),
                text = stringResource(R.string.authorization_center_text),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                color = colorResource(id = R.color.black_70),
            )

            Spacer(modifier = modifier.height(20.dp))

            Text(
                text = stringResource(R.string.email),
                color = colorResource(id = R.color.black_38),
                fontSize = 14.sp,
            )

            Spacer(modifier = modifier.height(2.dp))

            EmailTextField(
                email = email,
                modifier = modifier.fillMaxWidth(),
                setEmail = {
                    authorizationViewModel.setEmailText(it)
                    email = it
                },
            )

            Spacer(modifier = modifier.height(28.dp))

            Text(
                text = stringResource(R.string.password),
                color = colorResource(id = R.color.black_38),
                fontSize = 14.sp,
            )

            Spacer(modifier = modifier.height(2.dp))

            PasswordTextField(
                password,
                setPassword = {
                    authorizationViewModel.setPasswordText(it)
                    password = it
                },
            )

            Spacer(modifier = modifier.height(28.dp))

            Button(
                modifier =
                    modifier
                        .height(44.dp)
                        .fillMaxWidth(),
                enabled = authorizationViewModel.isAuthEnabledLiveData.value == true,
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.leaf)),
                onClick = {
                    login.invoke()
                },
            ) {
                Text(
                    text = stringResource(id = R.string.sign_in).uppercase(),
                    fontSize = 16.sp,
                    color = Color.White,
                )
            }

            Spacer(modifier = modifier.height(20.dp))

            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                HyperlinkText(
                    text = stringResource(id = R.string.forgot_password),
                )
                HyperlinkText(
                    text = stringResource(id = R.string.sign_up),
                )
            }
        }
    }
}
