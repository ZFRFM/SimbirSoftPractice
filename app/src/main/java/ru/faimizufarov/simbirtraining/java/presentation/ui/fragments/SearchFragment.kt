package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.FragmentSearchBinding
import ru.faimizufarov.simbirtraining.java.presentation.ui.adapters.SearchPagerAdapter

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding

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

        getFromSavedInstanceState(savedInstanceState)

        try {
            lifecycleScope.launch {
                binding.searchView.getQueryTextChangeStateFlow()
                    .debounce(500)
                    .collect { query ->
                        val bundle = bundleOf(QUERY_BUNDLE_KEY to query)
                        setFragmentResult(QUERY_KEY, bundle)
                    }
            }
        } catch (exception: Exception) {
            Toast.makeText(
                requireContext(),
                "Произошел сбой поиска",
                Toast.LENGTH_SHORT,
            ).show()
        }

        configureSearchView()
        configureViewPager()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        putInSavedInstanceState(outState)
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

    private fun getFromSavedInstanceState(savedInstanceState: Bundle?) {
        binding.searchView.setQuery(
            savedInstanceState?.getString(QUERY_SAVE_INSTANCE_STATE_KEY),
            true,
        )
    }

    private fun putInSavedInstanceState(outState: Bundle) {
        outState.putString(
            QUERY_SAVE_INSTANCE_STATE_KEY,
            binding.searchView.query.toString(),
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()

        const val QUERY_KEY = "QUERY_KEY"
        const val QUERY_BUNDLE_KEY = "QUERY_BUNDLE_KEY"
        const val QUERY_SAVE_INSTANCE_STATE_KEY = "QUERY_SAVE_INSTANCE_STATE_KEY"
    }
}

fun SearchView.getQueryTextChangeStateFlow(): StateFlow<String> {
    val query = MutableStateFlow("")

    setOnQueryTextListener(
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                query.value = newText
                return true
            }
        },
    )
    return query
}
