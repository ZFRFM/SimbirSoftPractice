package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.FragmentNewsFilterBinding
import ru.faimizufarov.simbirtraining.java.data.models.CategoryFilterItem
import ru.faimizufarov.simbirtraining.java.data.repositories.CategoryRepository
import ru.faimizufarov.simbirtraining.java.data.toFlow
import ru.faimizufarov.simbirtraining.java.presentation.ui.adapters.FilterAdapter

class NewsFilterFragment : Fragment() {
    private lateinit var binding: FragmentNewsFilterBinding
    private lateinit var itemDecoration: DividerItemDecoration

    private val newsFilterHolder: NewsFilterHolder = GlobalNewsFilterHolder
    private val categoriesRepository by lazy { CategoryRepository(requireContext().assets) }

    private val filterAdapter = FilterAdapter { filterItem ->
        if (filterItem.isChecked) {
            newsFilterHolder.removeFilter(filterItem.categoryId)
        } else {
            newsFilterHolder.setFilter(filterItem.categoryId)
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

        val dividerDrawable = ResourcesCompat.getDrawable(
            resources,
            R.drawable.divider_layer_search_result,
            null
        )
        dividerDrawable?.let(itemDecoration::setDrawable)

        with(binding) {
            contentDetailDescription.recyclerViewNewsFilterFragment
                .addItemDecoration(itemDecoration)

            contentDetailDescription.recyclerViewNewsFilterFragment.adapter = filterAdapter

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

        lifecycleScope.launch {
            val categoriesFlow = categoriesRepository.getCategoriesObservable().toFlow()
            val filtersFlow = newsFilterHolder.queuedFiltersFlow

            combine(categoriesFlow, filtersFlow) { categories, filters ->
                categories.map { category ->
                    CategoryFilterItem(
                        categoryId = category.id,
                        // FIXME: make distinction between global and local
                        title = category.globalName,
                        isChecked = filters.any { filter -> filter.categoryId == category.id }
                    )
                }
            }
                .flowOn(Dispatchers.IO)
                .collect(filterAdapter::submitList)
        }
    }

    companion object {
        fun newInstance(): NewsFilterFragment {
            return NewsFilterFragment()
        }
    }
}
