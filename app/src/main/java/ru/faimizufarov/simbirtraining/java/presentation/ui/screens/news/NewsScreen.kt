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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.faimizufarov.simbirtraining.java.domain.models.News
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news.composeunit.NewsItem
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news.composeunit.NewsTopAppBar
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.Colors

@Composable
fun NewsScreen(
    clickFilter: () -> Unit,
    clickItem: (News) -> Unit,
    modifier: Modifier = Modifier,
) {
    val newsViewModel: NewsViewModel = viewModel()
    val newsList = newsViewModel.newsLiveData.observeAsState(emptyList())

    Scaffold(
        modifier = modifier,
        topBar = {
            NewsTopAppBar(
                clickFilter = {
                    clickFilter.invoke()
                },
            )
        },
    ) { innerPadding ->
        NewsScreenBase(
            modifier = Modifier.padding(innerPadding),
            newsList = newsList.value,
            clickItem = clickItem,
        )
    }
}

@Composable
fun NewsScreenBase(
    newsList: List<News>,
    clickItem: (News) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = Colors.light_grey_two)
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
