package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news.composeunit

import android.util.TypedValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.leaf
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.officina_sans_extra_bold_c
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.twenty_first_font
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopAppBar(
    clickFilter: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // FIXME: the header gets to the top because of the custom height of the TopAppBar
    // Box wrapper corrects the situation

    Box(
        modifier = modifier.height(findActionBarThemeHeightInDp()),
    ) {
        CenterAlignedTopAppBar(
            modifier = Modifier,
            scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
            colors =
                TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = leaf,
                ),
            title = {
                Text(
                    text = stringResource(id = R.string.news),
                    color = Color.White,
                    fontFamily = officina_sans_extra_bold_c,
                    fontSize = twenty_first_font,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            },
            actions = {
                Box(
                    modifier =
                        Modifier
                            .fillMaxHeight()
                            .padding(end = 4.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    IconButton(
                        onClick = { clickFilter.invoke() },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_filter_24),
                            contentDescription = stringResource(id = R.string.filter),
                            tint = white,
                        )
                    }
                }
            },
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
