package ru.faimizufarov.news.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.faimizufarov.domain.models.News
import ru.faimizufarov.news.databinding.ItemNewsFragmentBinding

class NewsAdapter(
    private val onItemClick: (News) -> Unit,
) :
    ListAdapter<News, NewsViewHolder>(ItemCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): NewsViewHolder {
        val itemBinding =
            ItemNewsFragmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )

        return NewsViewHolder(itemBinding) { index ->
            onItemClick(currentList[index])
        }
    }

    override fun getItemCount() = currentList.size

    override fun onBindViewHolder(
        holder: NewsViewHolder,
        position: Int,
    ) {
        val news = currentList[position]
        holder.bind(news)
    }

    companion object ItemCallback : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(
            oldItem: News,
            newItem: News,
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: News,
            newItem: News,
        ) = oldItem == newItem
    }
}
