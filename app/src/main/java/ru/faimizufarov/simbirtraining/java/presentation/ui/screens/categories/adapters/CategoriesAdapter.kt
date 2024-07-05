package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.categories.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.faimizufarov.simbirtraining.databinding.ItemCategoryBinding
import ru.faimizufarov.simbirtraining.java.presentation.models.CategoryPresentation

class CategoriesAdapter :
    ListAdapter<CategoryPresentation, CategoriesViewHolder>(ItemCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CategoriesViewHolder {
        val itemBinding =
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )

        return CategoriesViewHolder(itemBinding)
    }

    override fun getItemCount() = currentList.size

    override fun onBindViewHolder(
        holder: CategoriesViewHolder,
        position: Int,
    ) {
        val category = currentList[position]
        holder.bind(category)
    }

    companion object ItemCallback : DiffUtil.ItemCallback<CategoryPresentation>() {
        override fun areItemsTheSame(
            oldItem: CategoryPresentation,
            newItem: CategoryPresentation,
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: CategoryPresentation,
            newItem: CategoryPresentation,
        ) = oldItem == newItem
    }
}
