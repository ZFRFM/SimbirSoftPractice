package ru.faimizufarov.simbirtraining.java.presentation.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.ItemHelpCategoryBinding
import ru.faimizufarov.simbirtraining.java.data.models.HelpCategoryEnum

class HelpCategoriesViewHolder(
    private val itemBinding: ItemHelpCategoryBinding,
) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(helpCategoryEnum: HelpCategoryEnum) {
        val (imageRes, titleRes) =
            when (helpCategoryEnum) {
                HelpCategoryEnum.CHILDREN -> R.drawable.children_category to R.string.children
                HelpCategoryEnum.ADULTS -> R.drawable.adults_category to R.string.adults
                HelpCategoryEnum.ELDERLY -> R.drawable.elderly_category to R.string.elderly
                HelpCategoryEnum.ANIMALS -> R.drawable.animals_category to R.string.animals
                HelpCategoryEnum.EVENTS -> R.drawable.events_category to R.string.events
            }

        itemBinding.imageViewHelpCategory.setImageResource(imageRes)
        itemBinding.textViewHelpCategory.setText(titleRes)
    }
}
