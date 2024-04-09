package ru.faimizufarov.simbirtraining.java.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.java.data.HelpCategoryEnum

class HelpCategoriesAdapter() :
    RecyclerView.Adapter<HelpCategoriesViewHolder> () {
    private val diffUtilCallback: DiffUtil.ItemCallback<HelpCategoryEnum> =
        object : DiffUtil.ItemCallback<HelpCategoryEnum>() {
            override fun areItemsTheSame(
                oldItem: HelpCategoryEnum,
                newItem: HelpCategoryEnum,
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: HelpCategoryEnum,
                newItem: HelpCategoryEnum,
            ) = oldItem == newItem
        }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtilCallback)

    fun setData(helpCategoryList: List<HelpCategoryEnum>) {
        asyncListDiffer.submitList(helpCategoryList)
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

    override fun getItemCount() = asyncListDiffer.currentList.size

    override fun onBindViewHolder(
        holder: HelpCategoriesViewHolder,
        position: Int,
    ) {
        val category = asyncListDiffer.currentList[position]
        holder.imageViewHelpCategory.setImageResource(category.imageView)
        holder.textViewHelpCategory.setText(category.nameCategory)
    }
}
