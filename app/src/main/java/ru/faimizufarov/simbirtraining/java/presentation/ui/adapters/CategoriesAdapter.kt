package ru.faimizufarov.simbirtraining.java.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.faimizufarov.simbirtraining.databinding.ItemHelpCategoryBinding
import ru.faimizufarov.simbirtraining.java.data.models.Category

class CategoriesAdapter : ListAdapter<Category, HelpCategoriesViewHolder>(ItemCallback) {
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

    companion object ItemCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(
            oldItem: Category,
            newItem: Category,
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Category,
            newItem: Category,
        ) = oldItem == newItem
    }
}
