package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.FragmentSearchViewPagerEventsBinding
import ru.faimizufarov.simbirtraining.java.data.models.News
import ru.faimizufarov.simbirtraining.java.presentation.ui.adapters.SearchResultEventsAdapter

class SearchViewPagerEventsFragment : Fragment() {
    private lateinit var binding: FragmentSearchViewPagerEventsBinding

    private val fetchDataScope = CoroutineScope(Dispatchers.IO)

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

                val searchViewEventsAdapter =
                    SearchResultEventsAdapter(
                        onItemClick = ::clickOnEventPosition,
                    )
                searchViewEventsAdapter.submitList(filteredEventsList)
                binding.recyclerViewEventsViewPager.adapter = searchViewEventsAdapter
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        fetchDataScope.cancel()
    }

    private fun clickOnEventPosition(news: News) {
        val errorHandler =
            CoroutineExceptionHandler { _, error: Throwable ->
                Toast.makeText(
                    requireContext(),
                    getString(R.string.search_item_click_exception),
                    Toast.LENGTH_SHORT,
                ).show()
            }

        fetchDataScope.launch(errorHandler) {
            delay(1000)
            throw Exception("Error on server side")
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
