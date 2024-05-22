package ru.faimizufarov.simbirtraining.java.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.faimizufarov.simbirtraining.databinding.ItemNewsFilterFragmentBinding
import ru.faimizufarov.simbirtraining.java.data.models.CategoryFilter

class FilterAdapter(
    private val filterList: List<CategoryFilter>,
    private val onItemClick: (CategoryFilter, Boolean) -> Unit,
) : RecyclerView.Adapter<FilterViewHolder>() {
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
            onItemClick(filterList[it], !filterList[it].checked)
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
