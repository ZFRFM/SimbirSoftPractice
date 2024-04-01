package ru.faimizufarov.simbirtraining.java.presentation.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.faimizufarov.simbirtraining.databinding.ItemNewsFragmentBinding
import ru.faimizufarov.simbirtraining.java.data.News

class NewsAdapter(
    val context: Context,
    private val onItemClick: (News) -> Unit,
) :
    RecyclerView.Adapter<NewsViewHolder>() {
    private var newsListClickable: List<News> = emptyList()

    private val diffUtilCallback: DiffUtil.ItemCallback<News> =
        object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(
                oldItem: News,
                newItem: News,
            ) = oldItem.id == newItem.id

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
        return NewsViewHolder(itemBinding, context) {
            onItemClick(newsListClickable[it])
        }
    }

    override fun getItemCount() = asyncListDiffer.currentList.size

    override fun onBindViewHolder(
        holder: NewsViewHolder,
        position: Int,
    ) {
        val news = asyncListDiffer.currentList[position]
        holder.bind(news)
    }
}
