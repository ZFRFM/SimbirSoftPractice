package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import ru.faimizufarov.simbirtraining.databinding.FragmentSearchViewPagerEventsBinding
import ru.faimizufarov.simbirtraining.java.data.News
import ru.faimizufarov.simbirtraining.java.presentation.ui.adapters.SearchResultEventsAdapter

class SearchViewPagerEventsFragment : Fragment() {
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

        parentFragment
            ?.setFragmentResultListener(SearchFragment.QUERY_KEY) { key, bundle ->
                val query = bundle.getString(SearchFragment.QUERY_BUNDLE_KEY) as CharSequence

                val filteredEventsList =
                    if (query == "") {
                        setVisibilityOfLocalUi(true)
                        listOf()
                    } else {
                        setVisibilityOfLocalUi(false)
                        NewsListHolder.getNewsList().filter { news: News ->
                            news.nameText.contains(
                                bundle.getString(SearchFragment.QUERY_BUNDLE_KEY) as CharSequence,
                                ignoreCase = true,
                            )
                        }
                    }

                val searchViewEventsAdapter = SearchResultEventsAdapter()
                searchViewEventsAdapter.submitList(filteredEventsList)
                binding.recyclerViewEventsViewPager.adapter = searchViewEventsAdapter
            }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchViewPagerEventsFragment()
    }

    private fun setVisibilityOfLocalUi(isVisible: Boolean) {
        with(binding) {
            imageViewZoom.isVisible = isVisible
            textViewWriteKeyWords.isVisible = isVisible
            linearLayoutKeyWords.isVisible = isVisible
        }
    }
}
