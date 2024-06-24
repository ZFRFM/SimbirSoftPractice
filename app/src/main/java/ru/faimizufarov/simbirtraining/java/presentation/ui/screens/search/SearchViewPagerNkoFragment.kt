package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.FragmentSearchViewPagerNkoBinding
import ru.faimizufarov.simbirtraining.java.data.models.Organization
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.search.adapters.SearchResultNkoAdapter
import kotlin.random.Random

class SearchViewPagerNkoFragment() : Fragment() {
    private lateinit var binding: FragmentSearchViewPagerNkoBinding

    private lateinit var itemDecoration: DividerItemDecoration
    private val searchResultAdapter = SearchResultNkoAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            FragmentSearchViewPagerNkoBinding
                .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewSearchResult.adapter = searchResultAdapter

        itemDecoration =
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL,
            )

        itemDecoration.setDrawable(
            resources.getDrawable(
                R.drawable.divider_layer_search_result,
                null,
            ),
        )
        binding.recyclerViewSearchResult.addItemDecoration(itemDecoration)
    }

    override fun onResume() {
        super.onResume()
        val listOfSearchResult = mutableListOf<Organization>()
        val randomListSize =
            Random.nextInt(1, organizationNameList.size)

        while (listOfSearchResult.size < randomListSize) {
            val random = organizationNameList.random()
            if (!listOfSearchResult.contains(random)) {
                listOfSearchResult.add(random)
            }
        }
        searchResultAdapter.submitList(listOfSearchResult.toList())
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchViewPagerNkoFragment()

        val organizationNameList =
            listOf(
                Organization(0, "Благотворительный Фонд Ф. Зуфарова"),
                Organization(1, "Благотворительный фонд Алины Кабаевой"),
                Organization(2, "«Во имя жизни»"),
                Organization(3, "Благотворительный Фонд В. Потанина"),
                Organization(4, "«Детские домики»"),
                Organization(5, "«Мозаика счастья»"),
                Organization(6, "«Я помогу»"),
                Organization(7, "«Детские мечты»"),
                Organization(8, "«Добро в мир»"),
                Organization(9, "«Старикам тут место»"),
                Organization(10, "Фонд помощи «Газпром»"),
                Organization(11, "«Сбербанк» рядом"),
                Organization(12, "Фонд помощи «Тинькофф»"),
            )
    }
}
