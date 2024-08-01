package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization.composeunit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.HelpTheme
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.leaf
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.officina_sans_extra_bold_c
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.twenty_first_font
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorizationTopAppBar(
    backPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .background(leaf)
                .height(56.dp)
                .fillMaxWidth(),
    ) {
        Box(
            modifier =
                Modifier
                    .align(Alignment.CenterStart)
                    .padding(end = 16.dp),
            contentAlignment = Alignment.Center,
        ) {
            IconButton(onClick = backPressed) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.authorization),
                    tint = white,
                )
            }
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(R.string.authorization),
            color = Color.White,
            fontFamily = officina_sans_extra_bold_c,
            fontSize = twenty_first_font,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
private fun AuthorizationTopAppBar_Preview() =
    HelpTheme {
        Surface {
            AuthorizationTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                backPressed = {},
            )
        }
    }
