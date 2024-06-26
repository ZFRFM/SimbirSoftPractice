package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.FragmentSearchBinding
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.search.adapters.SearchPagerAdapter

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            FragmentSearchBinding.inflate(
                inflater,
                container,
                false,
            )
        return binding.root
    }

    @OptIn(FlowPreview::class)
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        searchViewModel.queryLiveData.observe(viewLifecycleOwner) { query ->
            binding.searchView.setQuery(
                query,
                true,
            )
            val bundle = bundleOf(QUERY_BUNDLE_KEY to query)
            setFragmentResult(QUERY_KEY, bundle)
        }

        lifecycleScope.launch {
            binding.searchView.getQueryTextChangeStateFlow()
                .debounce(500)
                .collect { query ->
                    searchViewModel.setQuery(query)
                }
        }

        configureSearchView()
        configureViewPager()
    }

    private fun configureSearchView() {
        val searchManager =
            requireActivity()
                .getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.searchView
            .setSearchableInfo(
                searchManager.getSearchableInfo(requireActivity().componentName),
            )
    }

    private fun configureViewPager() {
        val fragmentViewPagerAdapter =
            SearchPagerAdapter(this, requireContext())

        val viewPager = binding.contentSearch.viewPager
        val tabLayout = binding.contentSearch.tabLayout

        viewPager.adapter = fragmentViewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            viewPager.adapter = fragmentViewPagerAdapter
            tab.text =
                if (position == 0) {
                    getString(R.string.by_events)
                } else {
                    getString(R.string.by_NGO)
                }
        }.attach()
    }

    companion object {
        fun newInstance() = SearchFragment()

        const val QUERY_KEY = "QUERY_KEY"
        const val QUERY_BUNDLE_KEY = "QUERY_BUNDLE_KEY"
    }
}

fun SearchView.getQueryTextChangeStateFlow(): Flow<String> =
    callbackFlow {
        setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    trySend(newText).isSuccess
                    return true
                }
            },
        )

        awaitClose {
            setOnQueryTextListener(null)
        }
    }
