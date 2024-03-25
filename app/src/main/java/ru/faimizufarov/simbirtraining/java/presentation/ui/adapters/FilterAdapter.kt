package ru.faimizufarov.simbirtraining.java.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.faimizufarov.simbirtraining.databinding.ItemNewsFilterFragmentBinding
import ru.faimizufarov.simbirtraining.java.data.Category
import ru.faimizufarov.simbirtraining.java.presentation.ui.fragments.NewsFilterHolder

class FilterAdapter(private val filterList: List<Category>) : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {
    class FilterViewHolder(val itemBinding: ItemNewsFilterFragmentBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(category: Category) {
            itemBinding.textViewFilterItem.setText(category.enumValue?.nameCategory ?: 0)
            itemBinding.switchFilterItem.isChecked = category.checked
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FilterViewHolder {
        val itemBinding =
            ItemNewsFilterFragmentBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterViewHolder(itemBinding)
    }

    override fun getItemCount() = filterList.size

    override fun onBindViewHolder(
        holder: FilterViewHolder,
        position: Int,
    ) {
        val filter = filterList[position]
        holder.bind(filter)
        holder.itemBinding.root.setOnClickListener {
            holder.itemBinding.switchFilterItem.isChecked =
                !holder.itemBinding.switchFilterItem.isChecked

            NewsFilterHolder.listFilters[position].checked =
                holder.itemBinding.switchFilterItem.isChecked
        }

        holder.itemBinding.switchFilterItem.setOnClickListener {
            NewsFilterHolder.listFilters[position].checked =
                holder.itemBinding.switchFilterItem.isChecked
        }
    }
}
