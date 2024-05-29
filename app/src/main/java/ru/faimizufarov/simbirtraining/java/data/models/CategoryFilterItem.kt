package ru.faimizufarov.simbirtraining.java.data.models

import androidx.recyclerview.widget.DiffUtil

data class CategoryFilterItem(
    val categoryId: String,
    val title: String,
    val isChecked: Boolean,
) {
    companion object ItemCallback : DiffUtil.ItemCallback<CategoryFilterItem>() {
        override fun areItemsTheSame(
            oldItem: CategoryFilterItem,
            newItem: CategoryFilterItem,
        ) = oldItem.categoryId == newItem.categoryId

        override fun areContentsTheSame(
            oldItem: CategoryFilterItem,
            newItem: CategoryFilterItem,
        ) = oldItem == newItem
    }
}
