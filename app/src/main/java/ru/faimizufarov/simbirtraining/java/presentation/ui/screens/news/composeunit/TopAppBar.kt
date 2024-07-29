package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news.composeunit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ru.faimizufarov.simbirtraining.R

@Suppress("ktlint:standard:function-naming")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    clickFilter: () -> Unit,
) {
    Box {
        TopAppBar(
            modifier = modifier,
            colors =
                TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorResource(id = R.color.leaf),
                ),
            title = { },
            actions = {
                IconButton(onClick = { clickFilter.invoke() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_filter_24),
                        contentDescription = stringResource(id = R.string.filter),
                        tint = Color.White,
                    )
                }
            },
        )
        Text(
            modifier =
                modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
            text = stringResource(id = R.string.news),
            color = Color.White,
            fontFamily =
                FontFamily(
                    Font(
                        R.font.officina_sans_extra_bold_c,
                        FontWeight.ExtraBold,
                    ),
                ),
            fontSize = 21.sp,
            textAlign = TextAlign.Center,
        )
    }
}
