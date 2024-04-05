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

        val fileInString = getCategoriesJson()
        val listOfCategories = getListOfCategories(fileInString)

        val recyclerView = binding.contentHelpCategories.recyclerViewHelpCategories
        recyclerView.adapter = HelpCategoriesAdapter(listOfCategories)
    }

    private fun getCategoriesJson() =
        requireContext()
            .applicationContext
            .assets
            .open("categories_list.json")
            .bufferedReader()
            .use { it.readText() }

    private fun getListOfCategories(json: String): List<HelpCategoryEnum> {
        return Json
            .decodeFromString<Array<CategoryResponse>>(json).map {
                when (it.id) {
                    0 -> HelpCategoryEnum.CHILDREN
                    1 -> HelpCategoryEnum.ADULTS
                    2 -> HelpCategoryEnum.ELDERLY
                    3 -> HelpCategoryEnum.ANIMALS
                    else -> HelpCategoryEnum.EVENTS
                }
            }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HelpCategoriesFragment()
    }
}
