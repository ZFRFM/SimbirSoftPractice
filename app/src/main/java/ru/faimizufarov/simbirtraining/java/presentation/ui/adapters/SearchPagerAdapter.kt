package ru.faimizufarov.simbirtraining.java.presentation.ui.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.faimizufarov.simbirtraining.java.presentation.ui.view.fragments.SearchViewPagerEventsFragment
import ru.faimizufarov.simbirtraining.java.presentation.ui.view.fragments.SearchViewPagerNkoFragment

class SearchPagerAdapter(fragment: Fragment, val context: Context) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            SearchViewPagerEventsFragment.newInstance()
        } else {
            SearchViewPagerNkoFragment.newInstance()
        }
    }
}
