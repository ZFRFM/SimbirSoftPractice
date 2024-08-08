package ru.faimizufarov.news.composeunit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.faimizufarov.core.theme.HelpTheme
import ru.faimizufarov.news.R

@Composable
fun NewsTopAppBar(clickFilter: () -> Unit) {
    Box(
        modifier =
        Modifier
            .background(MaterialTheme.colorScheme.primary)
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
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(R.string.news),
            style = MaterialTheme.typography.headlineMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
private fun NewsTopAppBar_Preview() =
    HelpTheme {
        Surface {
            NewsTopAppBar(
                clickFilter = { },
            )
        }
    }