package ru.faimizufarov.simbirtraining.java.presentation.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.ItemHelpCategoryBinding
import ru.faimizufarov.simbirtraining.java.data.models.Category
import ru.faimizufarov.simbirtraining.java.data.models.HelpCategoryEnum

class HelpCategoriesViewHolder(
    private val itemBinding: ItemHelpCategoryBinding,
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(category: Category) {
        val (image, title) = category.icon to category.globalName

        itemBinding.imageViewHelpCategory.setImageBitmap(image)
        itemBinding.textViewHelpCategory.text = title
    }
}
