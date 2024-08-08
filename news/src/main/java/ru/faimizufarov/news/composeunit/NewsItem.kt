package ru.faimizufarov.news.composeunit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import ru.faimizufarov.core.theme.Colors
import ru.faimizufarov.core.theme.HelpTheme
import ru.faimizufarov.news.R
import ru.faimizufarov.news.models.NewsCompose

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

    Card(
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        ConstraintLayout(
            modifier =
                modifier
                    .background(MaterialTheme.colorScheme.background)
                    .clickable { clickItem.invoke(news) },
        ) {
            val (
                glideImage,
                fadeImage,
                newsNameText,
                decorImage,
                newsDescriptionText,
                backgroundConstraintLayout,
            ) = createRefs()

            GlideImage(
                model = imagePath,
                contentDescription = null,
                modifier =
                    Modifier
                        .constrainAs(glideImage) {
                            start.linkTo(parent.start, margin = 4.dp)
                            end.linkTo(parent.end, margin = 4.dp)
                            top.linkTo(parent.top, margin = 4.dp)
                            height = Dimension.value(222.dp)
                            width = Dimension.fillToConstraints
                        },
                contentScale = ContentScale.FillWidth,
            )
            Image(
                painter = painterResource(id = R.drawable.fade),
                contentDescription = null,
                modifier =
                    Modifier
                        .fillMaxSize()
                        .constrainAs(fadeImage) {
                            start.linkTo(glideImage.start)
                            end.linkTo(glideImage.end)
                            top.linkTo(glideImage.top)
                            bottom.linkTo(glideImage.bottom)
                            height = Dimension.fillToConstraints
                            width = Dimension.fillToConstraints
                        },
                contentScale = ContentScale.FillBounds,
            )
            Text(
                modifier =
                    Modifier
                        .padding(horizontal = 40.dp)
                        .constrainAs(newsNameText) {
                            top.linkTo(fadeImage.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                text = news.nameText,
                color = Colors.blueGray,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
            )
            Image(
                modifier =
                    Modifier
                        .constrainAs(decorImage) {
                            top.linkTo(newsNameText.bottom, margin = 8.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                painter = painterResource(id = R.drawable.decor),
                contentDescription = null,
            )
            Text(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .constrainAs(newsDescriptionText) {
                            top.linkTo(decorImage.bottom, margin = 10.dp)
                            start.linkTo(parent.start, margin = 24.dp)
                            end.linkTo(parent.end, margin = 24.dp)
                        },
                text = news.descriptionText,
                textAlign = TextAlign.Center,
            )
            ConstraintLayout(
                constraintSet =
                    ConstraintSet {
                        val calendarIcon = createRefFor("calendarIcon")
                        val remainingTimeText = createRefFor("remainingTimeText")

                        createHorizontalChain(
                            calendarIcon,
                            remainingTimeText,
                            chainStyle = ChainStyle.Packed,
                        )

                        constrain(calendarIcon) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(remainingTimeText.start)
                        }

                        constrain(remainingTimeText) {
                            top.linkTo(calendarIcon.top)
                            start.linkTo(calendarIcon.end)
                            bottom.linkTo(calendarIcon.bottom)
                            end.linkTo(parent.end)
                        }
                    },
                modifier =
                    Modifier
                        .height(32.dp)
                        .background(MaterialTheme.colorScheme.secondary)
                        .constrainAs(backgroundConstraintLayout) {
                            top.linkTo(newsDescriptionText.bottom, margin = 16.dp)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        },
            ) {
                Image(
                    modifier = Modifier.layoutId("calendarIcon"),
                    painter = painterResource(id = R.drawable.icon_calendar),
                    contentDescription = null,
                )
                Text(
                    modifier = Modifier.layoutId("remainingTimeText"),
                    text = computeRemainingTime(news = news),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}

@Preview
@Composable
fun NewsItem_Preview() =
    HelpTheme {
        Surface {
            val news =
                NewsCompose(
                    id = "id",
                    nameText = "Выставка произведений искусства",
                    startDate = 0L,
                    finishDate = 0L,
                    descriptionText = "Данный текст является описанием вышеуказанного события и полностью его описывает",
                    status = 0L,
                    newsImages = listOf("images"),
                    categoryIds = listOf("1", "2"),
                    createAt = 1L,
                    phoneText = "8920*******",
                    addressText = "ул. Ново-Садовая 160М",
                    organisationText = "Check",
                )
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

    return if (startDate.dayOfYear == finishDate.dayOfYear) {
        val shortString =
            buildString {
                append("${startDate.month} ")
                append("${startDate.dayOfMonth}, ")
                append(startDate.year)
            }

        shortString
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

        longString
    }
}
