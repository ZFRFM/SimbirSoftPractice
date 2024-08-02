package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization.composeunit.AuthorizationTopAppBar
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.Colors
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.HelpTheme
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.Typography

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
    login: () -> Unit,
    setEmailText: (String) -> Unit,
    setPasswordText: (String) -> Unit,
    finish: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            AuthorizationTopAppBar(
                backPressed = finish,
            )
        },
    ) { innerPadding ->
        AuthorizationScreenBase(
            modifier = Modifier.padding(innerPadding),
            isSignInButtonEnabled = isSignInButtonEnabled,
            login = login,
            setEmailText = setEmailText,
            setPasswordText = setPasswordText,
        )
    }
}

@Composable
private fun AuthorizationScreenBase(
    login: () -> Unit,
    isSignInButtonEnabled: Boolean,
    setEmailText: (String) -> Unit,
    setPasswordText: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier =
            modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.authorization_top_text),
            textAlign = TextAlign.Center,
            fontSize = Typography.sixteenth_font,
            fontFamily = Typography.sans_serif_family,
            color = Colors.black_70,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Image(
                painter = painterResource(R.drawable.vk),
                contentDescription = null,
                modifier = Modifier.clickable { login.invoke() },
            )
            Image(painter = painterResource(R.drawable.fb), contentDescription = null)
            Image(painter = painterResource(R.drawable.ok), contentDescription = null)
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.authorization_center_text),
            textAlign = TextAlign.Center,
            fontSize = Typography.sixteenth_font,
            fontFamily = Typography.sans_serif_family,
            color = Colors.black_70,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.email),
            color = Colors.black_38,
            fontSize = Typography.fourteenth_font,
        )

        Spacer(modifier = Modifier.height(2.dp))

        EmailTextField(
            modifier = Modifier.fillMaxWidth(),
            email = email,
            onValueChange = {
                setEmailText(it)
                email = it
            },
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = stringResource(R.string.password),
            color = Colors.black_38,
            fontSize = Typography.fourteenth_font,
        )

        Spacer(modifier = Modifier.height(2.dp))

        PasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            password = password,
            onValueChange = {
                setPasswordText(it)
                password = it
            },
        )

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            modifier =
                Modifier
                    .height(44.dp)
                    .fillMaxWidth(),
            enabled = isSignInButtonEnabled,
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(Colors.leaf),
            onClick = login,
        ) {
            Text(
                text = stringResource(id = R.string.sign_in).uppercase(),
                fontSize = Typography.sixteenth_font,
                color = Colors.white,
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.forgot_password),
                fontSize = Typography.fourteenth_font,
                color = Colors.leaf,
                style =
                    TextStyle(
                        textDecoration = TextDecoration.Underline,
                    ),
            )
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.sign_up),
                fontSize = Typography.fourteenth_font,
                color = Colors.leaf,
                style =
                    TextStyle(
                        textDecoration = TextDecoration.Underline,
                    ),
            )
        }
    }
}

@Composable
private fun EmailTextField(
    email: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        modifier = modifier,
        value = email,
        onValueChange = { onValueChange(it) },
        maxLines = 1,
        placeholder = {
            Text(
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Start,
                text = stringResource(id = R.string.input_email),
                color = Colors.black_38,
            )
        },
        keyboardOptions =
            KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
            ),
        singleLine = true,
        colors =
            TextFieldDefaults.colors(
                focusedIndicatorColor = Colors.black,
                unfocusedIndicatorColor = Colors.black,
                focusedContainerColor = Colors.white,
                unfocusedContainerColor = Colors.white,
                cursorColor = Colors.black,
            ),
    )
}

@Composable
private fun PasswordTextField(
    password: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = password,
        onValueChange = onValueChange,
        maxLines = 1,
        placeholder = {
            Text(
                text = stringResource(id = R.string.input_password),
                color = Colors.black_38,
            )
        },
        singleLine = true,
        visualTransformation =
            if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { passwordHidden = !passwordHidden }) {
                val visibilityIcon =
                    if (passwordHidden) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }
                val description =
                    if (passwordHidden) {
                        stringResource(R.string.show_password)
                    } else {
                        stringResource(R.string.hide_password)
                    }
                Icon(imageVector = visibilityIcon, contentDescription = description)
            }
        },
        colors =
            TextFieldDefaults.colors(
                focusedIndicatorColor = Colors.black,
                unfocusedIndicatorColor = Colors.black,
                focusedContainerColor = Colors.white,
                unfocusedContainerColor = Colors.white,
            ),
    )
}

@Preview
@Composable
private fun EmailTextField_Preview() =
    HelpTheme {
        EmailTextField(
            email = "email",
            onValueChange = { },
        )
    }

@Preview
@Composable
private fun PasswordTextField_Preview() =
    HelpTheme {
        PasswordTextField(
            password = "password",
            onValueChange = { }
        )
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

@Preview
@Composable
fun AuthorizationScreenBasePreview() =
    HelpTheme {
        val email = "just_check_it_compiler"
        val password = "just_protect_it_compiler"
        Surface {
            AuthorizationScreenBase(
                login = { /*TODO*/ },
                isSignInButtonEnabled = email.isNotEmpty() && password.isNotEmpty(),
                setEmailText = { },
                setPasswordText = { },
            )
        }
    }
