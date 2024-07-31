package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization.composeunit

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import ru.faimizufarov.simbirtraining.R

@Suppress("ktlint:standard:function-naming")
@Composable
fun HyperlinkText(text: String) {
    Text(
        modifier = Modifier,
        text = text,
        fontSize = 14.sp,
        color = colorResource(id = R.color.leaf),
        style =
            TextStyle(
                textDecoration = TextDecoration.Underline,
            ),
    )
}
