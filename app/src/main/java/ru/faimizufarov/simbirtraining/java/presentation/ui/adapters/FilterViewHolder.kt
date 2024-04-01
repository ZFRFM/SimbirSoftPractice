package ru.faimizufarov.simbirtraining.java.presentation.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import ru.faimizufarov.simbirtraining.databinding.ItemNewsFilterFragmentBinding
import ru.faimizufarov.simbirtraining.java.data.Category

class FilterViewHolder(
    private val itemBinding: ItemNewsFilterFragmentBinding,
    onItemClicked: (Int) -> Unit,
) :
    RecyclerView.ViewHolder(itemBinding.root) {
    init {
        with(itemBinding) {
            root.setOnClickListener {
                onItemClicked(adapterPosition)
                itemBinding.switchFilterItem.isChecked = !itemBinding.switchFilterItem.isChecked
            }
            switchFilterItem.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }
    }

    fun bind(category: Category) {
        with(itemBinding) {
            textViewFilterItem.setText(category.enumValue?.nameCategory ?: 0)
            switchFilterItem.isChecked = category.checked
        }
    }
}
