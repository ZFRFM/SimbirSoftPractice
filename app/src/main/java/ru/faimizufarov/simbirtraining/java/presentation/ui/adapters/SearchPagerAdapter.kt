package ru.faimizufarov.simbirtraining.java.presentation.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.faimizufarov.simbirtraining.java.presentation.ui.fragments.SearchViewPagerFragment

class SearchPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return SearchViewPagerFragment.newInstance()
    }
}
