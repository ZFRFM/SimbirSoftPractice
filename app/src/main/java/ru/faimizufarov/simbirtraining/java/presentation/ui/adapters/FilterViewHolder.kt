package ru.faimizufarov.simbirtraining.java.presentation.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.ItemNewsFilterFragmentBinding
import ru.faimizufarov.simbirtraining.java.data.CategoryFilter
import ru.faimizufarov.simbirtraining.java.data.HelpCategoryEnum

class FilterViewHolder(
    private val itemBinding: ItemNewsFilterFragmentBinding,
    onItemClicked: (Int) -> Unit,
) : RecyclerView.ViewHolder(itemBinding.root) {
    init {
        with(itemBinding) {
            root.setOnClickListener {
                onItemClicked(adapterPosition)

                itemBinding.switchFilterItem.isChecked =
                    !itemBinding.switchFilterItem.isChecked
            }

            switchFilterItem.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }
    }

    fun bind(categoryFilter: CategoryFilter) {
        with(itemBinding) {
            val titleRes =
                when (categoryFilter.enumValue) {
                    HelpCategoryEnum.CHILDREN -> R.string.children
                    HelpCategoryEnum.ADULTS -> R.string.adults
                    HelpCategoryEnum.ELDERLY -> R.string.elderly
                    HelpCategoryEnum.ANIMALS -> R.string.animals
                    HelpCategoryEnum.EVENTS -> R.string.events
                    null -> error("Unknown category encountered, can't process it as a filter")
                }
            textViewFilterItem.setText(titleRes)
            switchFilterItem.isChecked = categoryFilter.checked
        }
    }
}
