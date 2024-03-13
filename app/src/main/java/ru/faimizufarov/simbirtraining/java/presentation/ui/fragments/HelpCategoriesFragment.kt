package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.faimizufarov.simbirtraining.databinding.FragmentHelpCategoriesBinding
import ru.faimizufarov.simbirtraining.java.data.HelpCategoryEnum
import ru.faimizufarov.simbirtraining.java.presentation.ui.adapters.HelpCategoriesAdapter

class HelpCategoriesFragment : Fragment() {
    private lateinit var binding: FragmentHelpCategoriesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHelpCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        val listOfCategories = HelpCategoryEnum.entries
        val recyclerView = binding.included.recyclerViewHelpCategories
        recyclerView.adapter = HelpCategoriesAdapter(listOfCategories)
    }

    companion object {
        @JvmStatic
        fun newInstance(): HelpCategoriesFragment {
            return HelpCategoriesFragment()
        }
    }
}
