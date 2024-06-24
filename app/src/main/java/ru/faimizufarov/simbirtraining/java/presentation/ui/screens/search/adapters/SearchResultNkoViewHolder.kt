package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.search.adapters

import androidx.recyclerview.widget.RecyclerView
import ru.faimizufarov.simbirtraining.databinding.ItemSearchResultBinding
import ru.faimizufarov.simbirtraining.java.data.models.Organization

class SearchResultNkoViewHolder(
    private val itemBinding: ItemSearchResultBinding,
) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(organization: Organization) {
        itemBinding.textViewItemSearchResult.text = organization.nameText
    }
}
