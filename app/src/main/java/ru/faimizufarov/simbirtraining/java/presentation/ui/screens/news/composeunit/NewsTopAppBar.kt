package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news.composeunit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.Colors
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.Typography

@Composable
fun NewsTopAppBar(
    clickFilter: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .background(Colors.leaf)
                .height(56.dp)
                .fillMaxWidth(),
    ) {
        Box(
            modifier =
                Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 12.dp),
            contentAlignment = Alignment.Center,
        ) {
            IconButton(onClick = clickFilter) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.baseline_filter_24),
                    contentDescription = stringResource(id = R.string.filter),
                    tint = Colors.white,
                )
            }
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(R.string.news),
            color = Color.White,
            fontFamily = Typography.officina_sans_extra_bold_c,
            fontSize = Typography.twenty_first_font,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
