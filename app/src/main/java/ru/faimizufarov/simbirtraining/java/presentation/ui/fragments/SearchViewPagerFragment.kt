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
import ru.faimizufarov.simbirtraining.java.presentation.ui.adapters.SearchResultAdapter

class SearchViewPagerFragment(val listSize: Int) : Fragment() {
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

        val listOfSearchResult = mutableListOf<OrganizationName>()

        while (listOfSearchResult.size < listSize) {
            val random = organizationNameList.random()
            if (!listOfSearchResult.contains(random)) listOfSearchResult.add(random)
        }

        val itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_layer_search_result, null))

        with(binding) {
            recyclerViewSearchResult.addItemDecoration(itemDecoration)
            recyclerViewSearchResult.adapter = SearchResultAdapter(listOfSearchResult)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(listSize: Int) = SearchViewPagerFragment(listSize)

        val organizationNameList =
            listOf(
                OrganizationName("Благотворительный Фонд Ф. Зуфарова"),
                OrganizationName("Благотворительный фонд Алины Кабаевой"),
                OrganizationName("«Во имя жизни»"),
                OrganizationName("Благотворительный Фонд В. Потанина"),
                OrganizationName("«Детские домики»"),
                OrganizationName("«Мозаика счастья»"),
                OrganizationName("«Я помогу»"),
                OrganizationName("«Детские мечты»"),
                OrganizationName("«Добро в мир»"),
                OrganizationName("«Старикам тут место»"),
                OrganizationName("Фонд помощи «Газпром»"),
                OrganizationName("«Сбербанк» рядом"),
                OrganizationName("Фонд помощи «Тинькофф»"),
            )
    }
}
