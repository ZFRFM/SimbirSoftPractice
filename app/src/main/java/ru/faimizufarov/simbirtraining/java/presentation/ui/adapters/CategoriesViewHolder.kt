package ru.faimizufarov.simbirtraining.java.presentation.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import ru.faimizufarov.simbirtraining.databinding.ItemCategoryBinding
import ru.faimizufarov.simbirtraining.java.data.models.Category

class CategoriesViewHolder(
    private val itemBinding: ItemCategoryBinding,
) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(category: Category) {
        val (image, title) = category.icon to category.localizedName
        itemBinding.imageViewHelpCategory.setImageBitmap(image)
        itemBinding.textViewHelpCategory.text = title
    }
}
