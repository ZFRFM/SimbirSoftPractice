package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.search.adapters

import androidx.recyclerview.widget.RecyclerView
import ru.faimizufarov.data.models.Organization
import ru.faimizufarov.simbirtraining.databinding.ItemSearchResultBinding

class SearchResultNkoViewHolder(
    private val itemBinding: ItemSearchResultBinding,
) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(organization: Organization) {
        itemBinding.textViewItemSearchResult.text = organization.nameText
    }
}
