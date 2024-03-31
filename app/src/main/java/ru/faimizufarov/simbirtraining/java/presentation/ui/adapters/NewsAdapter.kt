package ru.faimizufarov.simbirtraining.java.presentation.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getString
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.ItemNewsFragmentBinding
import ru.faimizufarov.simbirtraining.java.data.News

class NewsAdapter(val context: Context) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    var onItemClick: ((News) -> Unit)? = null
    var newsListClickable: List<News> = emptyList()

    inner class NewsViewHolder(val itemBinding: ItemNewsFragmentBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        init {
            itemBinding.root.setOnClickListener {
                onItemClick?.invoke(newsListClickable[adapterPosition])
            }
            itemBinding.imageViewNewsFade.setOnClickListener {
                onItemClick?.invoke(newsListClickable[adapterPosition])
            }
        }

        fun bind(news: News) {
            Glide.with(context).load(news.newsImage).into(itemBinding.imageViewNews)
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
                            append(getString(context, R.string.news_remaining_time))
                            append(" $remainingDays")
                            append(" (${news.startDate.dayOfMonth}.${news.startDate.monthNumber} - ")
                            append("${news.finishDate.dayOfMonth}.${news.finishDate.monthNumber})")
                        } else {
                            append(getString(context, R.string.news_event_finished))
                        }
                    }
                itemBinding.textViewNewsRemainingTime.setText(longString)
            }
        }
    }

    private val diffUtilCallback: DiffUtil.ItemCallback<News> =
        object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(
                oldItem: News,
                newItem: News,
            ) = oldItem === newItem

            override fun areContentsTheSame(
                oldItem: News,
                newItem: News,
            ) = oldItem == newItem
        }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtilCallback)

    fun setData(newsList: List<News>) {
        asyncListDiffer.submitList(newsList)
        newsListClickable = newsList
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

    override fun getItemCount() = asyncListDiffer.currentList.size

    override fun onBindViewHolder(
        holder: NewsViewHolder,
        position: Int,
    ) {
        val news = asyncListDiffer.currentList[position]
        holder.bind(news)
        holder.itemBinding.root.setOnClickListener {
            onItemClick?.invoke(news)
        }
    }
}
