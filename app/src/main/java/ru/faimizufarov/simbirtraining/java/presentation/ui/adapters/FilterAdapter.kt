package ru.faimizufarov.simbirtraining.java.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.faimizufarov.simbirtraining.databinding.ItemNewsFilterFragmentBinding
import ru.faimizufarov.simbirtraining.java.data.Category
import ru.faimizufarov.simbirtraining.java.presentation.ui.fragments.NewsFilterHolder

class FilterAdapter(
    private val filterList: List<Category>,
    private val onItemClick: (Category, Boolean) -> Unit,
) :
    RecyclerView.Adapter<FilterViewHolder>() {
    private var categoryListClickable: List<Category> = NewsFilterHolder.getFilterList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FilterViewHolder {
        val itemBinding =
            ItemNewsFilterFragmentBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterViewHolder(itemBinding) {
            onItemClick(categoryListClickable[it], !categoryListClickable[it].checked)
        }
    }

    override fun getItemCount() = filterList.size

    override fun onBindViewHolder(
        holder: FilterViewHolder,
        position: Int,
    ) {
        val filter = filterList[position]
        holder.bind(filter)
    }
}
