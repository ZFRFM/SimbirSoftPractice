package ru.faimizufarov.simbirtraining.java.presentation.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import ru.faimizufarov.simbirtraining.databinding.ItemNewsFilterFragmentBinding
import ru.faimizufarov.simbirtraining.java.data.models.CategoryFilterItem

class CategoryFilterViewHolder(
    private val itemBinding: ItemNewsFilterFragmentBinding,
    onItemClick: (position: Int) -> Unit,
) : RecyclerView.ViewHolder(itemBinding.root) {
    init {
        with(itemBinding) {
            root.setOnClickListener {
                onItemClick(adapterPosition)

                itemBinding.switchFilterItem.isChecked =
                    !itemBinding.switchFilterItem.isChecked
            }

            switchFilterItem.setOnClickListener {
                onItemClick(adapterPosition)

                itemBinding.switchFilterItem.isChecked =
                    !itemBinding.switchFilterItem.isChecked
            }
        }
    }

    fun bind(categoryFilter: CategoryFilterItem) {
        with(itemBinding) {
            textViewFilterItem.text = categoryFilter.title
            switchFilterItem.isChecked = categoryFilter.isChecked
        }
    }
}
