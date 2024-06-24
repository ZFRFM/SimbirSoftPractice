package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news_filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.launch
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.FragmentNewsFilterBinding
import ru.faimizufarov.simbirtraining.java.data.models.CategoryFilterItem
import ru.faimizufarov.simbirtraining.java.presentation.ui.holders.GlobalNewsFilterHolder
import ru.faimizufarov.simbirtraining.java.presentation.ui.holders.NewsFilterHolder
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news_filter.adapters.CategoryFilterAdapter

class NewsFilterFragment : Fragment() {
    private lateinit var binding: FragmentNewsFilterBinding
    private lateinit var itemDecoration: DividerItemDecoration
    private val disposables = CompositeDisposable()

    private val newsFilterHolder: NewsFilterHolder = GlobalNewsFilterHolder

    private val newsFilterViewModel:
        NewsFilterViewModel by viewModels { NewsFilterViewModel.Factory }

    private val categoryFilterAdapter =
        CategoryFilterAdapter { filterItem ->
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

        val dividerDrawable =
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.divider_layer_search_result,
                null,
            )
        dividerDrawable?.let(itemDecoration::setDrawable)

        with(binding) {
            contentDetailDescription.recyclerViewNewsFilterFragment
                .addItemDecoration(itemDecoration)

            contentDetailDescription
                .recyclerViewNewsFilterFragment
                .adapter = categoryFilterAdapter

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
            newsFilterHolder.queuedFiltersFlow.collect { filters ->
                val categories = newsFilterViewModel.categoriesRepository.value?.getCategoryList()
                val categoryList =
                    categories?.map { category ->
                        CategoryFilterItem(
                            categoryId = category.id,
                            title = category.title,
                            isChecked =
                                filters.any { filter ->
                                    filter.categoryId == category.id
                                },
                        )
                    }
                categoryFilterAdapter.submitList(categoryList)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    companion object {
        fun newInstance(): NewsFilterFragment {
            return NewsFilterFragment()
        }
    }
}
