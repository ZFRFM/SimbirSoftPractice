package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.google.android.material.tabs.TabLayoutMediator
import com.jakewharton.rxbinding4.widget.queryTextChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.FragmentSearchBinding
import ru.faimizufarov.simbirtraining.java.presentation.ui.adapters.SearchPagerAdapter
import java.util.concurrent.TimeUnit

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val disposables = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        getFromSavedInstanceState(savedInstanceState)

        val searchSubject = PublishSubject.create<String>()
        val searchViewObservable =
            binding.searchView.queryTextChanges()
                .debounce(500, TimeUnit.MILLISECONDS)
                .map { it.toString() }

        disposables.add(
            searchViewObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { query ->
                    searchSubject.onNext(query)
                },
        )

        disposables.add(
            searchSubject.subscribe { query ->
                val bundle = bundleOf(QUERY_BUNDLE_KEY to query)
                setFragmentResult(QUERY_KEY, bundle)
            },
        )

        configureSearchView()
        configureViewPager()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        putInSavedInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        disposables.clear()
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
        val fragmentViewPagerAdapter = SearchPagerAdapter(this, requireContext())
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
        outState.putString(QUERY_SAVE_INSTANCE_STATE_KEY, binding.searchView.query.toString())
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()

        const val QUERY_KEY = "QUERY_KEY"
        const val QUERY_BUNDLE_KEY = "QUERY_BUNDLE_KEY"
        const val QUERY_SAVE_INSTANCE_STATE_KEY = "QUERY_SAVE_INSTANCE_STATE_KEY"
    }
}
