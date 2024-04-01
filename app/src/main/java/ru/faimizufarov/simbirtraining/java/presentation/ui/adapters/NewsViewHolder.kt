package ru.faimizufarov.simbirtraining.java.presentation.ui.adapters

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.ItemNewsFragmentBinding
import ru.faimizufarov.simbirtraining.java.data.News

class NewsViewHolder(
    private val itemBinding: ItemNewsFragmentBinding,
    val context: Context,
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
        Glide.with(context).load(news.newsImageUrl).into(itemBinding.imageViewNews)
        with(itemBinding) {
            textViewNewsName.setText(news.nameText)
            textViewNewsDescription.setText(news.descriptionText)
        }
        if (news.startDate.dayOfYear == news.finishDate.dayOfYear) {
            val shortString =
                buildString {
                    append("${news.startDate.month} ")
                    append("${news.startDate.dayOfMonth}, ")
                    append(news.startDate.year)
                }
            itemBinding.textViewNewsRemainingTime.setText(shortString)
        } else {
            val timeZone: TimeZone = TimeZone.currentSystemDefault()
            val today = Clock.System.todayIn(timeZone).toEpochDays()
            val remainingDays = news.finishDate.date.toEpochDays() - today
            val longString =
                buildString {
                    if (remainingDays >= 0) {
                        append(ContextCompat.getString(context, R.string.news_remaining_time))
                        append(" $remainingDays")
                        append(" (${news.startDate.dayOfMonth}.${news.startDate.monthNumber} - ")
                        append("${news.finishDate.dayOfMonth}.${news.finishDate.monthNumber})")
                    } else {
                        append(ContextCompat.getString(context, R.string.news_event_finished))
                    }
                }
            itemBinding.textViewNewsRemainingTime.setText(longString)
        }
    }
}
