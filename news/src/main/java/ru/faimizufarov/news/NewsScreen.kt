package ru.faimizufarov.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.faimizufarov.core.theme.HelpTheme
import ru.faimizufarov.news.composeunit.NewsItem
import ru.faimizufarov.news.composeunit.NewsTopAppBar
import ru.faimizufarov.news.models.NewsCompose
import ru.faimizufarov.news.models.toNewsCompose

@Composable
fun NewsScreen(
    clickFilter: () -> Unit,
    clickItem: (NewsCompose) -> Unit,
) {
    val newsViewModel: NewsViewModel = viewModel()
    val newsList = newsViewModel.newsLiveData.observeAsState(emptyList())

    Scaffold(
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
            newsList = newsList.value.map { news -> news.toNewsCompose() },
            clickItem = clickItem,
        )
    }
}

@Composable
fun NewsScreenBase(
    newsList: List<NewsCompose>,
    clickItem: (NewsCompose) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
                .padding(horizontal = 8.dp),
    ) {
        items(newsList) { news ->
            Spacer(modifier = Modifier.height(8.dp))
            NewsItem(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                news = news,
                clickItem = clickItem,
            )
        }
    }
}

@Preview
@Composable
fun NewsScreen_Preview() =
    HelpTheme {
        val newsList =
            List(10) { i ->
                NewsCompose(
                    id = "$i",
                    nameText = "News ${i + 1}",
                    startDate = 0L,
                    finishDate = 0L,
                    descriptionText =
                        "У попа была собака, он её любил. Она съела кусок мяса, он её убил. В землю закопал, на могиле написал: ".repeat(
                            i,
                        ),
                    status = 0L,
                    newsImages = listOf("stub"),
                    categoryIds = listOf("1", "2"),
                    createAt = 1L,
                    phoneText = "8920******* ".repeat(i),
                    addressText = "ул. Ново-Садовая 160М ".repeat(i),
                    organisationText = "Check ".repeat(i),
                )
            }
        NewsScreenBase(newsList = newsList, clickItem = { })
    }
