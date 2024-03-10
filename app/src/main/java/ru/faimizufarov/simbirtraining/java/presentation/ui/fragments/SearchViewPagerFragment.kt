package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.FragmentSearchViewPagerBinding
import ru.faimizufarov.simbirtraining.java.data.OrganizationName
import ru.faimizufarov.simbirtraining.java.data.OrganizationNameList
import ru.faimizufarov.simbirtraining.java.presentation.ui.adapters.SearchResultAdapter

class SearchViewPagerFragment : Fragment() {
    private lateinit var binding: FragmentSearchViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSearchViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerViewSearchResult
        val listOfSearchResult = mutableListOf<OrganizationName>()

        while (listOfSearchResult.size < 9) {
            val random = OrganizationNameList().organizationNameList.random()
            if (!listOfSearchResult.contains(random)) listOfSearchResult.add(random)
        }

        val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_layer_search_result, null))
        recyclerView.addItemDecoration(itemDecoration)

        recyclerView.adapter = SearchResultAdapter(listOfSearchResult)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchViewPagerFragment()
    }
}
