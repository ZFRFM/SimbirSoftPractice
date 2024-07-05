package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.categories.adapters

import androidx.recyclerview.widget.RecyclerView
import ru.faimizufarov.simbirtraining.databinding.ItemCategoryBinding
import ru.faimizufarov.simbirtraining.java.presentation.models.CategoryPresentation

class CategoriesViewHolder(
    private val itemBinding: ItemCategoryBinding,
) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(category: CategoryPresentation) {
        val (image, title) = category.image to category.title

        itemBinding.imageViewHelpCategory.setImageBitmap(image)
        itemBinding.textViewHelpCategory.text = title
    }
}
