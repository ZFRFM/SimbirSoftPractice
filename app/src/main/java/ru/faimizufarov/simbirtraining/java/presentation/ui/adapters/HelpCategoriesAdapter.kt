package ru.faimizufarov.simbirtraining.java.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.faimizufarov.simbirtraining.databinding.ItemHelpCategoryBinding
import ru.faimizufarov.simbirtraining.java.data.models.HelpCategoryEnum

class HelpCategoriesAdapter :
    ListAdapter<HelpCategoryEnum, HelpCategoriesViewHolder>(ItemCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HelpCategoriesViewHolder {
        val itemBinding =
            ItemHelpCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )

        return HelpCategoriesViewHolder(itemBinding)
    }

    override fun getItemCount() = currentList.size

    override fun onBindViewHolder(
        holder: HelpCategoriesViewHolder,
        position: Int,
    ) {
        val category = currentList[position]
        holder.bind(category)
    }

    companion object ItemCallback : DiffUtil.ItemCallback<HelpCategoryEnum>() {
        override fun areItemsTheSame(
            oldItem: HelpCategoryEnum,
            newItem: HelpCategoryEnum,
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: HelpCategoryEnum,
            newItem: HelpCategoryEnum,
        ) = oldItem == newItem
    }
}
