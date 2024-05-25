package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.FragmentNewsFilterBinding
import ru.faimizufarov.simbirtraining.java.data.models.CategoryFilter
import ru.faimizufarov.simbirtraining.java.data.models.CategoryFilterItem
import ru.faimizufarov.simbirtraining.java.data.models.categoryEnumFromId
import ru.faimizufarov.simbirtraining.java.data.models.toTitleRes
import ru.faimizufarov.simbirtraining.java.presentation.ui.adapters.FilterAdapter

class NewsFilterFragment : Fragment() {
    private lateinit var binding: FragmentNewsFilterBinding
    private lateinit var itemDecoration: DividerItemDecoration

    private val newsFilterHolder: NewsFilterHolder = GlobalNewsFilterHolder

    private val filterAdapter =
        FilterAdapter { categoryFilterItem ->
            val categoryEnum = categoryFilterItem.categoryId.categoryEnumFromId()
            if (categoryFilterItem.isChecked) {
                newsFilterHolder.removeFilter(categoryEnum)
            } else {
                newsFilterHolder.setFilter(categoryEnum)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            FragmentNewsFilterBinding
                .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)

        itemDecoration.setDrawable(
            resources.getDrawable(R.drawable.divider_layer_search_result, null),
        )

        with(binding) {
            contentDetailDescription.recyclerViewNewsFilterFragment
                .addItemDecoration(itemDecoration)

            contentDetailDescription
                .recyclerViewNewsFilterFragment
                .adapter = filterAdapter

            imageViewOk.setOnClickListener {
                newsFilterHolder.confirm()
                parentFragmentManager
                    .beginTransaction()
                    .remove(this@NewsFilterFragment).commit()
            }

            imageViewBack.setOnClickListener {
                newsFilterHolder.cancel()
                parentFragmentManager
                    .beginTransaction()
                    .remove(this@NewsFilterFragment).commit()
            }
        }

        filterAdapter.submitList(newsFilterHolder.queuedFilters.map { filter -> filter.toItem() })
        newsFilterHolder.setOnFiltersEditedListener {
            filterAdapter.submitList(newsFilterHolder.queuedFilters.map { filter -> filter.toItem() })
        }
    }

    private fun CategoryFilter.toItem() =
        CategoryFilterItem(
            enumValue?.id.toString(),
            enumValue?.toTitleRes()?.let(::getString) ?: error("No suitable title found"),
            checked,
        )

    companion object {
        fun newInstance(): NewsFilterFragment {
            return NewsFilterFragment()
        }
    }
}
