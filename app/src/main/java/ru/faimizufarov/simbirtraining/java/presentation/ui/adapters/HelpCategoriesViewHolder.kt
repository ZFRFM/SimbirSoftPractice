package ru.faimizufarov.simbirtraining.java.presentation.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import ru.faimizufarov.simbirtraining.databinding.ItemHelpCategoryBinding
import ru.faimizufarov.simbirtraining.java.data.HelpCategoryEnum

class HelpCategoriesViewHolder(
    private val itemBinding: ItemHelpCategoryBinding,
) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(helpCategoryEnum: HelpCategoryEnum) {
        itemBinding.imageViewHelpCategory.setImageResource(helpCategoryEnum.imageView)
        itemBinding.textViewHelpCategory.setText(helpCategoryEnum.nameCategory)
    }
}
