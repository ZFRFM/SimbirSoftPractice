package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news.composeunit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.faimizufarov.simbirtraining.java.domain.models.News
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.light_grey_two

@Composable
fun NewsScreenBase(
    newsList: List<News>,
    clickItem: (News) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = light_grey_two)
                .padding(horizontal = 8.dp),
    ) {
        items(newsList) { news ->
            Spacer(modifier = Modifier.height(8.dp))
            NewsItem(
                modifier = Modifier.fillMaxWidth(),
                news = news,
                clickItem = clickItem,
            )
        }
    }
}
