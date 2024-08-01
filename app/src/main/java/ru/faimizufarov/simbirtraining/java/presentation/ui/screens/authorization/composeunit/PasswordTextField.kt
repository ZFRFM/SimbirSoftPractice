package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization.composeunit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.black
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.black_38
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.white

@Composable
fun PasswordTextField(
    password: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = password,
        onValueChange = { onValueChange(it) },
        maxLines = 1,
        placeholder = {
            Text(
                text = stringResource(id = R.string.input_password),
                color = black_38,
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
                focusedIndicatorColor = black,
                unfocusedIndicatorColor = black,
                focusedContainerColor = white,
                unfocusedContainerColor = white,
            ),
    )
}
