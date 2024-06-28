package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news_filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.FragmentNewsFilterBinding
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news_filter.adapters.CategoryFilterAdapter

class NewsFilterFragment : Fragment() {
    private lateinit var binding: FragmentNewsFilterBinding
    private lateinit var itemDecoration: DividerItemDecoration
    private val disposables = CompositeDisposable()

    private val newsFilterViewModel:
        NewsFilterViewModel by viewModels { NewsFilterViewModel.Factory(requireContext()) }

    private val categoryFilterAdapter =
        CategoryFilterAdapter { filterItem ->
            if (filterItem.isChecked) {
                newsFilterViewModel.removeFilter(filterItem.categoryId)
            } else {
                newsFilterViewModel.setFilter(filterItem.categoryId)
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
                newsFilterViewModel.confirm()
                parentFragmentManager
                    .beginTransaction()
                    .remove(this@NewsFilterFragment).commit()
            }

            imageViewBack.setOnClickListener {
                newsFilterViewModel.cancel()
                parentFragmentManager
                    .beginTransaction()
                    .remove(this@NewsFilterFragment).commit()
            }
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        newsFilterViewModel.newsFilters.observe(viewLifecycleOwner) {
            newsFilterViewModel.collectNewsFilters()
        }

        newsFilterViewModel.categoryFiltersLiveData
            .observe(viewLifecycleOwner) { categoryFilterList ->
                categoryFilterAdapter.submitList(categoryFilterList)
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
