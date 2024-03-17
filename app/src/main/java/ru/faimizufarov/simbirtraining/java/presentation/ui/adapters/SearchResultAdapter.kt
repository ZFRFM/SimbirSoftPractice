package ru.faimizufarov.simbirtraining.java.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.java.data.OrganizationName

class SearchResultAdapter() : RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>() {
    private var list: List<OrganizationName> = listOf()

    class SearchResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewSearchResult: TextView = itemView.findViewById(R.id.textViewItemSearchResult)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SearchResultViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_search_result, parent, false)
        return SearchResultViewHolder(itemView)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(
        holder: SearchResultViewHolder,
        position: Int,
    ) {
        holder.textViewSearchResult.text = list[position].name
    }

    fun addItems(inputList: List<OrganizationName>) {
        list = inputList
        notifyDataSetChanged()
    }
}
