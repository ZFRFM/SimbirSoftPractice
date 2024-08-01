package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization.composeunit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.black
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.black_38
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("ktlint:standard:function-naming")
@Composable
fun EmailTextField(
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
                color = black_38,
            )
        },
        keyboardOptions =
            KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
            ),
        singleLine = true,
        colors =
            TextFieldDefaults.colors(
                focusedIndicatorColor = black,
                unfocusedIndicatorColor = black,
                focusedContainerColor = white,
                unfocusedContainerColor = white,
                cursorColor = black,
            ),
    )
}
