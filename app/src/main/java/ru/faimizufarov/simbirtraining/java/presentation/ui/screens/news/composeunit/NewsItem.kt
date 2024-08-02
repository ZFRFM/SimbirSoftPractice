package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news.composeunit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.java.domain.models.News
import ru.faimizufarov.simbirtraining.java.presentation.models.NewsCompose
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.Colors
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.HelpTheme
import ru.faimizufarov.simbirtraining.java.presentation.ui.theme.Typography

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsItem(
    news: NewsCompose,
    clickItem: (NewsCompose) -> Unit,
    modifier: Modifier = Modifier,
) {
    val imagePath =
        if (news.newsImages.first().contains("http")) {
            news.newsImages.first()
        } else {
            "file:///android_asset/${news.newsImages.first()}"
        }

    Column(
        modifier =
            modifier
                .background(colorResource(id = R.color.white))
                .clickable { clickItem.invoke(news) },
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            GlideImage(
                model = imagePath,
                contentDescription = null,
                modifier = Modifier,
                contentScale = ContentScale.Fit,
            )
            Image(
                painter = painterResource(id = R.drawable.fade),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
            )
            Text(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(start = 40.dp, end = 40.dp)
                        .offset(y = 44.dp),
                text = news.nameText,
                color = Colors.blue_grey,
                fontSize = Typography.twenty_first_font,
                fontFamily = Typography.officina_sans_extra_bold_c,
                textAlign = TextAlign.Center,
            )
        }
        Image(
            modifier =
                Modifier
                    .padding(top = 52.dp)
                    .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.decor),
            contentDescription = null,
        )
        Text(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 16.dp)
                    .padding(horizontal = 24.dp),
            text = news.descriptionText,
            textAlign = TextAlign.Center,
        )
        Row(
            modifier =
                Modifier
                    .background(Colors.turtle_green)
                    .fillMaxWidth()
                    .height(32.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Center,
        ) {
            Image(
                modifier = Modifier.align(Alignment.CenterVertically),
                painter = painterResource(id = R.drawable.icon_calendar),
                contentDescription = null,
            )
            Text(
                modifier =
                    Modifier
                        .align(Alignment.CenterVertically),
                text = computeRemainingTime(news = news),
                color = Colors.white,
                fontSize = Typography.twelfth_font,
            )
        }
    }
}

@Preview
@Composable
fun NewsItem_Preview() =
    HelpTheme {
        val news =
            NewsCompose(
                id = "id",
                nameText = "nameText",
                startDate = 0L,
                finishDate = 0L,
                descriptionText = "descriptionText",
                status = 0L,
                newsImages = listOf("images"),
                categoryIds = listOf("1", "2"),
                createAt = 1L,
                phoneText = "8920*******",
                addressText = "ул. Ново-Садовая 160М",
                organisationText = "Check",
            )
        Surface {
            NewsItem(
                news = news,
                clickItem = { },
            )
        }
    }

@Composable
fun computeRemainingTime(news: NewsCompose): String {
    val startDate =
        Instant.fromEpochMilliseconds(news.startDate)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    val finishDate =
        Instant.fromEpochMilliseconds(news.finishDate)
            .toLocalDateTime(TimeZone.currentSystemDefault())

    if (startDate.dayOfYear == finishDate.dayOfYear) {
        val shortString =
            buildString {
                append("${startDate.month} ")
                append("${startDate.dayOfMonth}, ")
                append(startDate.year)
            }

        return shortString
    } else {
        val timeZone: TimeZone = TimeZone.currentSystemDefault()
        val today = Clock.System.todayIn(timeZone).toEpochDays()
        val remainingDays = finishDate.date.toEpochDays() - today

        val longString =
            buildString {
                if (remainingDays >= 0) {
                    append(
                        ContextCompat.getString(
                            LocalContext.current,
                            R.string.news_remaining_time,
                        ),
                    )
                    append(" $remainingDays")
                    append(" (${startDate.dayOfMonth}.${startDate.monthNumber} - ")
                    append("${finishDate.dayOfMonth}.${finishDate.monthNumber})")
                } else {
                    append(
                        ContextCompat.getString(
                            LocalContext.current,
                            R.string.news_event_finished,
                        ),
                    )
                }
            }

        return longString
    }
}
