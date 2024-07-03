package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.categories

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.faimizufarov.simbirtraining.databinding.FragmentHelpCategoriesBinding
import ru.faimizufarov.simbirtraining.java.data.models.Category
import ru.faimizufarov.simbirtraining.java.presentation.services.CategoryLoaderService
import ru.faimizufarov.simbirtraining.java.presentation.services.CategoryLoaderServiceConnection
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.categories.adapters.CategoriesAdapter

class CategoriesFragment : Fragment() {
    private lateinit var binding: FragmentHelpCategoriesBinding
    private val categoriesAdapter by lazy {
        CategoriesAdapter()
    }

    private val categoriesViewModel: CategoriesViewModel by viewModels()

    private var listOfCategories: List<Category>? = null

    private var isServiceBound = false
    private val connection =
        CategoryLoaderServiceConnection { categories ->
            activity?.runOnUiThread { showCategories(categories) }
        }

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

        binding.contentHelpCategories.recyclerViewHelpCategories.adapter = categoriesAdapter

        initialLoading()
        categoriesViewModel.categoriesLiveData.observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.submitList(categories)
        }
    }

    override fun onStop() {
        super.onStop()
        if (isServiceBound) {
            activity?.unbindService(connection)
            isServiceBound = false
        }
    }

    private fun startJsonLoaderService() {
        val serviceIntent = Intent(requireContext(), CategoryLoaderService::class.java)

        activity?.startService(serviceIntent)

        Intent(requireContext(), CategoryLoaderService::class.java).also { intent ->
            isServiceBound =
                requireActivity()
                    .bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    private fun showCategories(categories: List<Category>) {
        listOfCategories = categories
        categoriesAdapter.submitList(categories)
        categoriesViewModel.setCategories(categories)
        binding.contentHelpCategories.progressBar.isVisible = false
    }

    private fun initialLoading() {
        binding.contentHelpCategories.progressBar.isVisible = true
        startJsonLoaderService()
    }

    companion object {
        fun newInstance() = CategoriesFragment()
    }
}
