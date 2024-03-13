package ru.faimizufarov.simbirtraining.java.presentation.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.faimizufarov.simbirtraining.java.presentation.ui.fragments.SearchViewPagerFragment
import kotlin.random.Random

class SearchPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        val randomListSize = Random.nextInt(1, SearchViewPagerFragment.organizationNameList.size)
        return SearchViewPagerFragment.newInstance(randomListSize)
    }
}
