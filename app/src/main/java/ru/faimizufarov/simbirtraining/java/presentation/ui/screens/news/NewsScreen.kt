package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.java.domain.models.News
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news.composeunit.NewsItem
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news.composeunit.TopAppBar

@Suppress("ktlint:standard:function-naming")
@Composable
fun NewsScreen(
    newsViewModel: NewsViewModel,
    modifier: Modifier = Modifier,
    clickFilter: () -> Unit,
    clickItem: (News) -> Unit,
) {
    val newsList by newsViewModel.newsLiveData.observeAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = modifier,
                clickFilter = {
                    clickFilter.invoke()
                },
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier =
                modifier
                    .fillMaxSize()
                    .background(color = colorResource(id = R.color.light_grey_two))
                    .padding(innerPadding)
                    .padding(horizontal = 8.dp),
        ) {
            items(newsList) { news ->
                Spacer(modifier = modifier.height(8.dp))
                NewsItem(
                    news = news,
                    clickItem = clickItem,
                )
            }
        }
    }
}
