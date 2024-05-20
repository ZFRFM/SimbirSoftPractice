package ru.faimizufarov.simbirtraining.java.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.faimizufarov.simbirtraining.databinding.ItemNewsFilterFragmentBinding
import ru.faimizufarov.simbirtraining.java.data.models.CategoryFilter
import ru.faimizufarov.simbirtraining.java.presentation.ui.fragments.NewsFilterHolder

class FilterAdapter(
    private val filterList: List<CategoryFilter>,
    private val onItemClick: (CategoryFilter, Boolean) -> Unit,
) :
    RecyclerView.Adapter<FilterViewHolder>() {
    private var categoryFilterListClickable: List<CategoryFilter> = NewsFilterHolder.getFilterList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FilterViewHolder {
        val itemBinding =
            ItemNewsFilterFragmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )

        return FilterViewHolder(itemBinding) {
            onItemClick(
                categoryFilterListClickable[it],
                !categoryFilterListClickable[it].checked,
            )
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
