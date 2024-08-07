package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.faimizufarov.data.models.Organization
import ru.faimizufarov.simbirtraining.databinding.ItemSearchResultBinding

class SearchResultNkoAdapter :
    ListAdapter<Organization, SearchResultNkoViewHolder>(ItemCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SearchResultNkoViewHolder {
        val itemBinding =
            ItemSearchResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )

        return SearchResultNkoViewHolder(itemBinding)
    }

    override fun onBindViewHolder(
        holder: SearchResultNkoViewHolder,
        position: Int,
    ) {
        val organization = currentList[position]
        holder.bind(organization)
    }

    companion object ItemCallback : DiffUtil.ItemCallback<Organization>() {
        override fun areItemsTheSame(
            oldItem: Organization,
            newItem: Organization,
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Organization,
            newItem: Organization,
        ) = oldItem == newItem
    }
}
