package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization.composeunit

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.fourteenth_font
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.leaf

@Suppress("ktlint:standard:function-naming")
@Composable
fun HyperlinkText(text: String) {
    Text(
        modifier = Modifier,
        text = text,
        fontSize = fourteenth_font,
        color = leaf,
        style =
            TextStyle(
                textDecoration = TextDecoration.Underline,
            ),
    )
}
