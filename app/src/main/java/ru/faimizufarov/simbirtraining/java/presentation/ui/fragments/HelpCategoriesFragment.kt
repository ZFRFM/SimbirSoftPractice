package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.serialization.json.Json
import ru.faimizufarov.simbirtraining.databinding.FragmentHelpCategoriesBinding
import ru.faimizufarov.simbirtraining.java.data.CategoryResponse
import ru.faimizufarov.simbirtraining.java.data.HelpCategoryEnum
import ru.faimizufarov.simbirtraining.java.presentation.ui.adapters.HelpCategoriesAdapter

class HelpCategoriesFragment : Fragment() {
    private lateinit var binding: FragmentHelpCategoriesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHelpCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val fileInString by lazy {
            requireContext()
                .applicationContext
                .assets
                .open("categories_list.json")
                .bufferedReader()
                .use { it.readText() }
        }

        val listOfCategories =
            Json
                .decodeFromString<Array<CategoryResponse>>(fileInString).map {
                    when (it.id) {
                        0 -> HelpCategoryEnum.CHILDREN
                        1 -> HelpCategoryEnum.ADULTS
                        2 -> HelpCategoryEnum.ELDERLY
                        3 -> HelpCategoryEnum.ANIMALS
                        else -> HelpCategoryEnum.EVENTS
                    }
                }

        val recyclerView = binding.contentHelpCategories.recyclerViewHelpCategories
        recyclerView.adapter = HelpCategoriesAdapter(listOfCategories)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HelpCategoriesFragment()
    }
}
