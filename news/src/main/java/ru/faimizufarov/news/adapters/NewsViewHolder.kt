package ru.faimizufarov.news.adapters

import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import ru.faimizufarov.domain.models.News
import ru.faimizufarov.news.R
import ru.faimizufarov.news.databinding.ItemNewsFragmentBinding

class NewsViewHolder(
    private val itemBinding: ItemNewsFragmentBinding,
    onItemClicked: (Int) -> Unit,
) :
    RecyclerView.ViewHolder(itemBinding.root) {
    init {
        with(itemBinding) {
            root.setOnClickListener {
                onItemClicked(adapterPosition)
            }

            imageViewNewsFade.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }
    }

    fun bind(news: News) {
        val context = itemBinding.root.context
        val newsImageAsset = news.newsImages.first()

        if (!news.newsImages.first().startsWith("images/news")) {
            Glide
                .with(itemBinding.root.context)
                .load(news.newsImages.first())
                .into(itemBinding.imageViewNews)
        } else {
            Glide
                .with(itemBinding.root.context)
                .load(Uri.parse("file:///android_asset/$newsImageAsset"))
                .into(itemBinding.imageViewNews)
        }

        with(itemBinding) {
            textViewNewsName.text = news.nameText
            textViewNewsDescription.text = news.descriptionText
        }

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

            itemBinding.textViewNewsRemainingTime.text = shortString
        } else {
            val timeZone: TimeZone = TimeZone.currentSystemDefault()

            val today = Clock.System.todayIn(timeZone).toEpochDays()

            val remainingDays = finishDate.date.toEpochDays() - today

            val longString =
                buildString {
                    if (remainingDays >= 0) {
                        append(ContextCompat.getString(context, R.string.news_remaining_time))
                        append(" $remainingDays")
                        append(" (${startDate.dayOfMonth}.${startDate.monthNumber} - ")
                        append("${finishDate.dayOfMonth}.${finishDate.monthNumber})")
                    } else {
                        append(ContextCompat.getString(context, R.string.news_event_finished))
                    }
                }

            itemBinding.textViewNewsRemainingTime.text = longString
        }
    }
}
