package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization.composeunit

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import ru.faimizufarov.simbirtraining.R

@Suppress("ktlint:standard:function-naming")
@Composable
fun EmailTextField(
    email: String,
    modifier: Modifier = Modifier,
    setEmail: (String) -> Unit,
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = email,
        onValueChange = { setEmail(it) },
        maxLines = 1,
        placeholder = {
            Text(
                modifier = modifier,
                textAlign = TextAlign.Start,
                text = stringResource(id = R.string.input_email),
                color = colorResource(id = R.color.black_38),
            )
        },
        keyboardOptions =
            KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
            ),
        singleLine = true,
    )
}
