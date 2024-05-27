package ru.faimizufarov.simbirtraining.java.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.faimizufarov.simbirtraining.databinding.ItemCategoryBinding
import ru.faimizufarov.simbirtraining.java.data.models.Category

class CategoriesAdapter :
    ListAdapter<Category, CategoriesViewHolder>(ItemCallback) {
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
