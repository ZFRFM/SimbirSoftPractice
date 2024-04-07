package ru.faimizufarov.simbirtraining.java.presentation.ui.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.faimizufarov.simbirtraining.R

class HelpCategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageViewHelpCategory: ImageView = itemView.findViewById(R.id.imageViewHelpCategory)
    val textViewHelpCategory: TextView = itemView.findViewById(R.id.textViewHelpCategory)
}
