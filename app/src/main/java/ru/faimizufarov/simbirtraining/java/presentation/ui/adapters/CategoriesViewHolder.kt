package ru.faimizufarov.simbirtraining.java.presentation.ui.adapters

import android.content.res.AssetManager
import android.graphics.BitmapFactory
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.faimizufarov.simbirtraining.databinding.ItemCategoryBinding
import ru.faimizufarov.simbirtraining.java.data.models.Category

class CategoriesViewHolder(
    private val itemBinding: ItemCategoryBinding,
    private val assetManager: AssetManager,
) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(category: Category) {
        val (image, title) = category.imagePath to category.localizedName

        try {
            val iconStream = assetManager.open(image)
            val iconBitmap = BitmapFactory.decodeStream(iconStream)
            itemBinding.imageViewHelpCategory.setImageBitmap(iconBitmap)
        } catch (exception: Exception) {
            Glide
                .with(itemBinding.root.context)
                .load(image)
                .into(itemBinding.imageViewHelpCategory)
        }

        itemBinding.textViewHelpCategory.text = title
    }
}
