package ru.faimizufarov.simbirtraining.java.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.java.data.HelpCategoryEnum
import kotlin.enums.EnumEntries

class HelpCategoriesAdapter(private val categories: EnumEntries<HelpCategoryEnum>) :
    RecyclerView.Adapter<HelpCategoriesAdapter.HelpCategoriesViewHolder> () {
    class HelpCategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewHelpCategory: ImageView = itemView.findViewById(R.id.imageViewHelpCategory)
        val textViewHelpCategory: TextView = itemView.findViewById(R.id.textViewHelpCategory)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HelpCategoriesViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_help_category, parent, false)
        return HelpCategoriesViewHolder(itemView)
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(
        holder: HelpCategoriesViewHolder,
        position: Int,
    ) {
        holder.imageViewHelpCategory.setImageResource(categories[position].imageView)
        holder.textViewHelpCategory.text = categories[position].name
    }
}
