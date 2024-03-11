package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import ru.faimizufarov.simbirtraining.databinding.FragmentSearchBinding
import ru.faimizufarov.simbirtraining.java.presentation.ui.adapters.SearchPagerAdapter

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding

    private lateinit var fragmentViewPagerAdapter: SearchPagerAdapter
    private lateinit var viewPager: ViewPager2

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
        val searchManager =
            requireActivity()
                .getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.searchView
            .setSearchableInfo(
                searchManager.getSearchableInfo(requireActivity().componentName),
            )

        fragmentViewPagerAdapter = SearchPagerAdapter(this)
        viewPager = binding.included.viewPager
        viewPager.adapter = fragmentViewPagerAdapter

        val tabLayout = binding.included.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = if (position == 0) "По мероприятиям" else "По НКО"
        }.attach()
    }

    companion object {
        @JvmStatic
        fun newInstance(): SearchFragment {
            val fragment = SearchFragment()
            return fragment
        }
    }
}
