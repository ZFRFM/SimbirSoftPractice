package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.DividerItemDecoration
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.FragmentNewsFilterBinding
import ru.faimizufarov.simbirtraining.java.data.Category
import ru.faimizufarov.simbirtraining.java.data.HelpCategoryEnum
import ru.faimizufarov.simbirtraining.java.presentation.ui.adapters.FilterAdapter

class NewsFilterFragment : Fragment() {
    private lateinit var binding: FragmentNewsFilterBinding
    private lateinit var itemDecoration: DividerItemDecoration

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentNewsFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        itemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_layer_search_result, null))
        binding.included.recyclerViewNewsFilterFragment.addItemDecoration(itemDecoration)

        binding.included.recyclerViewNewsFilterFragment.adapter = FilterAdapter(listFilters)

        binding.imageViewOk.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }

        val bundle = bundleOf(APPLIED_FILTERS to listFilters)
        parentFragment?.setFragmentResult(APPLIED_FILTERS_RESULT, bundle)
    }

    companion object {
        const val APPLIED_FILTERS = "APPLIED_FILTERS"
        const val APPLIED_FILTERS_RESULT = "APPLIED_FILTERS_RESULT"

        fun newInstance() = NewsFilterFragment()

        val listFilters =
            listOf(
                Category(enumValue = HelpCategoryEnum.CHILDREN, checked = false),
                Category(enumValue = HelpCategoryEnum.ADULTS, checked = true),
                Category(enumValue = HelpCategoryEnum.ELDERLY, checked = true),
                Category(enumValue = HelpCategoryEnum.ANIMALS, checked = true),
                Category(enumValue = HelpCategoryEnum.EVENTS, checked = true),
            )
    }
}
