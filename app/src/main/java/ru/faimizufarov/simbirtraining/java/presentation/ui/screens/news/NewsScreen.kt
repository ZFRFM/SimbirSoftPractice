package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.faimizufarov.simbirtraining.java.domain.models.News
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news.composeunit.NewsScreenBase
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news.composeunit.NewsTopAppBar

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
                modifier = Modifier,
                clickFilter = {
                    clickFilter.invoke()
                },
            )
        },
    ) { innerPadding ->
        NewsScreenBase(
            newsList = newsList.value,
            clickItem = clickItem,
            modifier = Modifier.padding(innerPadding),
        )
    }
}
