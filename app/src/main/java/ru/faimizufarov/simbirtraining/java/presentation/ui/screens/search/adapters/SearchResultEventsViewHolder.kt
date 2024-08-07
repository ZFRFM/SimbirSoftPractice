package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.search.adapters

import androidx.recyclerview.widget.RecyclerView
import ru.faimizufarov.domain.models.News
import ru.faimizufarov.simbirtraining.databinding.ItemSearchResultBinding

class SearchResultEventsViewHolder(
    private val itemBinding: ItemSearchResultBinding,
    onItemClicked: (Int) -> Unit,
) : RecyclerView.ViewHolder(itemBinding.root) {
    init {
        with(itemBinding) {
            root.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }
    }

    fun bind(news: News) {
        itemBinding.textViewItemSearchResult.text = news.nameText
    }
}
