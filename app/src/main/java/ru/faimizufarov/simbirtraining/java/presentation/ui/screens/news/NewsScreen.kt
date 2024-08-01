package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.faimizufarov.simbirtraining.java.domain.models.News
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news.composeunit.NewsItem
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news.composeunit.TopAppBar
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.light_grey_two

@Suppress("ktlint:standard:function-naming")
@Composable
fun NewsScreen(
    newsViewModel: NewsViewModel,
    clickFilter: () -> Unit,
    clickItem: (News) -> Unit,
    modifier: Modifier = Modifier,
) {
    val newsList by newsViewModel.newsLiveData.observeAsState(emptyList())

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier,
                clickFilter = {
                    clickFilter.invoke()
                },
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(color = light_grey_two)
                    .padding(horizontal = 8.dp)
                    .padding(innerPadding),
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
}
