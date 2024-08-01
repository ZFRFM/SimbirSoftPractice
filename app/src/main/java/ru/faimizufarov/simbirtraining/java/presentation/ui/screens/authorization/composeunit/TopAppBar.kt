package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.authorization.composeunit

import android.util.TypedValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.officina_sans_extra_bold_c
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.twenty_first_font
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.white

@Suppress("ktlint:standard:function-naming")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    backPressed: () -> Unit,
) {
    Box {
        TopAppBar(
            modifier = modifier.height(findActionBarThemeHeightInDp()),
            colors =
                TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorResource(id = R.color.leaf),
                ),
            title = { },
            navigationIcon = {
                Box(
                    modifier =
                        Modifier
                            .fillMaxHeight()
                            .padding(end = 4.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    IconButton(onClick = backPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.authorization),
                            tint = white,
                        )
                    }
                }
            },
        )
        Text(
            modifier =
                Modifier
                    .align(Alignment.Center),
            text = stringResource(R.string.authorization),
            color = Color.White,
            fontFamily = officina_sans_extra_bold_c,
            fontSize = twenty_first_font,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun findActionBarThemeHeightInDp(): Dp {
    val typedValue = TypedValue()
    val theme = LocalContext.current.theme
    theme.resolveAttribute(android.R.attr.actionBarSize, typedValue, true)
    val actionBarSizeInPx =
        TypedValue
            .complexToDimensionPixelSize(
                typedValue.data,
                LocalContext.current.resources.displayMetrics,
            )
    val actionBarSizeInDp =
        with(LocalContext.current.resources.displayMetrics) {
            actionBarSizeInPx / density
        }.dp
    return actionBarSizeInDp
}
