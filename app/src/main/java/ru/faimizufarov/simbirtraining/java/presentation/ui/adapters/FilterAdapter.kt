package ru.faimizufarov.simbirtraining.java.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.faimizufarov.simbirtraining.databinding.ItemNewsFilterFragmentBinding
import ru.faimizufarov.simbirtraining.java.data.FilterCriteria

class FilterAdapter(private val filterList: List<FilterCriteria>) : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {
    class FilterViewHolder(private val itemBinding: ItemNewsFilterFragmentBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(filterCriteria: FilterCriteria) {
            itemBinding.textViewFilterItem.setText(filterCriteria.name)
            itemBinding.switchFilterItem.isChecked = filterCriteria.checked
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
    }
}
