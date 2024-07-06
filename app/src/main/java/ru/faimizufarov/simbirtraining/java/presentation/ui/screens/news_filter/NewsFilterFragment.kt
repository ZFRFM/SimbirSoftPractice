package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news_filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.FragmentNewsFilterBinding
import ru.faimizufarov.simbirtraining.java.App
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news_filter.adapters.CategoryFilterAdapter
import javax.inject.Inject

class NewsFilterFragment : Fragment() {
    private lateinit var binding: FragmentNewsFilterBinding
    private lateinit var itemDecoration: DividerItemDecoration

    @Inject
    lateinit var newsFilterViewModelFactory: NewsFilterViewModelFactory
    private lateinit var newsFilterViewModel: NewsFilterViewModel

    private val categoryFilterAdapter =
        CategoryFilterAdapter { filterItem ->
            if (filterItem.isChecked) {
                newsFilterViewModel.removeFilter(filterItem.categoryId)
            } else {
                newsFilterViewModel.setFilter(filterItem.categoryId)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App)
            .appComponent
            .injectNewsFilterFragment(this)
        newsFilterViewModel =
            ViewModelProvider(
                this,
                newsFilterViewModelFactory,
            )[NewsFilterViewModel::class]
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
            contentNewsFilter.recyclerViewNewsFilterFragment
                .addItemDecoration(itemDecoration)

            contentNewsFilter
                .recyclerViewNewsFilterFragment
                .adapter = categoryFilterAdapter

            imageViewOk.setOnClickListener {
                newsFilterViewModel.confirmFilters()
                parentFragmentManager
                    .beginTransaction()
                    .remove(this@NewsFilterFragment).commit()
            }

            imageViewBack.setOnClickListener {
                newsFilterViewModel.cancelFilters()
                parentFragmentManager
                    .beginTransaction()
                    .remove(this@NewsFilterFragment).commit()
            }
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        newsFilterViewModel.categoryFiltersLiveData
            .observe(viewLifecycleOwner) { categoryFilterList ->
                categoryFilterAdapter.submitList(categoryFilterList)
                binding.contentNewsFilter.bottomDivider.visibility = View.VISIBLE
            }
    }

    companion object {
        fun newInstance(): NewsFilterFragment {
            return NewsFilterFragment()
        }
    }
}
