package ru.faimizufarov.simbirtraining.java.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import ru.faimizufarov.simbirtraining.databinding.ItemNewsFragmentBinding
import ru.faimizufarov.simbirtraining.java.data.News

class NewsAdapter(private val newsList: List<News>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    class NewsViewHolder(private val itemBinding: ItemNewsFragmentBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(news: News) {
            itemBinding.imageViewNews.setImageResource(news.imageViewNews)
            itemBinding.textViewNewsName.setText(news.textViewName)
            itemBinding.textViewNewsDescription.setText(news.textViewDescription)
            if (news.startDate.dayOfMonth == news.finishDate.dayOfMonth) {
                itemBinding.textViewNewsRemainingTime
                    .setText("${news.startDate.month} ${news.startDate.dayOfMonth}, ${news.startDate.year}")
            } else {
                val timeZone: TimeZone = TimeZone.currentSystemDefault()
                val today = Clock.System.todayIn(timeZone).dayOfMonth
                val remainingDays = news.finishDate.dayOfMonth - today
                itemBinding.textViewNewsRemainingTime
                    .setText(
                        "Осталось $remainingDays дней " +
                            "(${news.startDate.dayOfMonth}.${news.startDate.monthNumber} - " +
                            "${news.finishDate.dayOfMonth}.${news.finishDate.monthNumber})",
                    )
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): NewsViewHolder {
        val itemBinding =
            ItemNewsFragmentBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(itemBinding)
    }

    override fun getItemCount() = newsList.size

    override fun onBindViewHolder(
        holder: NewsViewHolder,
        position: Int,
    ) {
        val news = newsList[position]
        holder.bind(news)
    }
}
