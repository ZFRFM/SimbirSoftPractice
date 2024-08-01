package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization.composeunit

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.HelpTheme
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.black_38
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.black_70
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.fourteenth_font
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.leaf
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.sans_serif_family
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.sixteenth_font
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.white

@Composable
fun AuthorizationScreenBase(
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
            fontSize = sixteenth_font,
            fontFamily = sans_serif_family,
            color = black_70,
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
            fontSize = sixteenth_font,
            fontFamily = sans_serif_family,
            color = black_70,
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.email),
            color = black_38,
            fontSize = fourteenth_font,
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
            color = black_38,
            fontSize = fourteenth_font,
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
            colors = ButtonDefaults.buttonColors(leaf),
            onClick = login,
        ) {
            Text(
                text = stringResource(id = R.string.sign_in).uppercase(),
                fontSize = sixteenth_font,
                color = white,
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
                fontSize = fourteenth_font,
                color = leaf,
                style =
                    TextStyle(
                        textDecoration = TextDecoration.Underline,
                    ),
            )
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.sign_up),
                fontSize = fourteenth_font,
                color = leaf,
                style =
                    TextStyle(
                        textDecoration = TextDecoration.Underline,
                    ),
            )
        }
    }
}

@Preview
@Composable
fun AuthorizationScreenBasePreview() {
    HelpTheme {
        Surface {
            AuthorizationScreenBase(
                login = { /*TODO*/ },
                isSignInButtonEnabled = true,
                setEmailText = { },
                setPasswordText = { },
            )
        }
    }
}
