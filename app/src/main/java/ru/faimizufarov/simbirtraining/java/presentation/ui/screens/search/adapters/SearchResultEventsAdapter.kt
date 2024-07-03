package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.faimizufarov.simbirtraining.databinding.ItemSearchResultBinding
import ru.faimizufarov.simbirtraining.java.data.models.News

class SearchResultEventsAdapter(
    private val onItemClick: (News) -> Unit,
) :
    ListAdapter<News, SearchResultEventsViewHolder>(ItemCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SearchResultEventsViewHolder {
        val itemBinding =
            ItemSearchResultBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

        return SearchResultEventsViewHolder(itemBinding) { index ->
            onItemClick(currentList[index])
        }
    }

    override fun onBindViewHolder(
        holder: SearchResultEventsViewHolder,
        position: Int,
    ) {
        val event = currentList[position]
        holder.bind(event)
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
