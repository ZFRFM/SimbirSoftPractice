package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.FragmentSearchViewPagerEventsBinding
import ru.faimizufarov.simbirtraining.java.domain.models.News
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.search.adapters.SearchResultEventsAdapter

class SearchViewPagerEventsFragment : Fragment() {
    private val searchViewPagerEventsViewModel: SearchViewPagerEventsViewModel
        by viewModels { SearchViewPagerEventsViewModel.Factory(requireContext()) }

    private lateinit var binding: FragmentSearchViewPagerEventsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            FragmentSearchViewPagerEventsBinding
                .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
    }

    private fun observeViewModel() {
        searchViewPagerEventsViewModel.newsList.observe(viewLifecycleOwner) { newsList ->
            parentFragment
                ?.setFragmentResultListener(SearchFragment.QUERY_KEY) { key, bundle ->
                    val query = bundle.getString(SearchFragment.QUERY_BUNDLE_KEY) as CharSequence

                    val filteredEventsList = setFilteredEvents(query, newsList)

                    val searchViewEventsAdapter =
                        SearchResultEventsAdapter(
                            onItemClick = ::clickOnEventPosition,
                        )
                    searchViewEventsAdapter.submitList(filteredEventsList)
                    binding.recyclerViewEventsViewPager.adapter = searchViewEventsAdapter
                }
        }

        searchViewPagerEventsViewModel
            .isNewsAvailable
            .observe(viewLifecycleOwner) { isNewsAvailable ->
                if (!isNewsAvailable) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.search_item_click_exception),
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun clickOnEventPosition(news: News) {
        searchViewPagerEventsViewModel.clickOnEventPosition()
    }

    private fun setVisibilityOfLocalUi(isVisible: Boolean) {
        with(binding) {
            imageViewZoom.isVisible = isVisible
            textViewWriteKeyWords.isVisible = isVisible
            linearLayoutKeyWords.isVisible = isVisible
        }
    }

    private fun setFilteredEvents(
        query: CharSequence,
        newsList: List<News>,
    ) = if (query == "") {
        setVisibilityOfLocalUi(true)
        listOf()
    } else {
        setVisibilityOfLocalUi(false)
        newsList.filterByQuery(query)
    }

    private fun List<News>.filterByQuery(query: CharSequence) =
        this.filter { news: News ->
            news.nameText.contains(
                query,
                ignoreCase = true,
            )
        }

    companion object {
        fun newInstance() = SearchViewPagerEventsFragment()
    }
}
