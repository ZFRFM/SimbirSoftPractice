package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import ru.faimizufarov.simbirtraining.databinding.FragmentHelpCategoriesBinding
import ru.faimizufarov.simbirtraining.java.data.models.HelpCategoryEnum
import ru.faimizufarov.simbirtraining.java.presentation.ui.adapters.HelpCategoriesAdapter
import ru.faimizufarov.simbirtraining.java.services.CategoryLoaderService
import ru.faimizufarov.simbirtraining.java.services.CategoryLoaderServiceConnection

class HelpCategoriesFragment : Fragment() {
    private lateinit var binding: FragmentHelpCategoriesBinding
    private val helpCategoriesAdapter = HelpCategoriesAdapter()

    private var listOfCategories: List<HelpCategoryEnum>? = null

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

        binding.contentHelpCategories.recyclerViewHelpCategories.adapter = helpCategoriesAdapter
        if (savedInstanceState != null && savedInstanceState.isCategoriesSaved) {
            getFromSavedInstanceState(savedInstanceState)
        } else {
            loadIfAbsentState()
        }
    }

    private val Bundle.isCategoriesSaved: Boolean
        get() = containsKey(LIST_OF_CATEGORIES_KEY)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val categoriesArray = listOfCategories?.let(::ArrayList) ?: return
        outState.putParcelableArrayList(LIST_OF_CATEGORIES_KEY, categoriesArray)
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

    private fun showCategories(categories: List<HelpCategoryEnum>) {
        listOfCategories = categories
        helpCategoriesAdapter.submitList(categories)
        binding.contentHelpCategories.progressBar.isVisible = false
    }

    private fun getFromSavedInstanceState(savedInstanceState: Bundle) {
        val arrayList =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                savedInstanceState.getParcelable(
                    LIST_OF_CATEGORIES_KEY,
                    ArrayList::class.java,
                )
            } else {
                savedInstanceState.getParcelable(LIST_OF_CATEGORIES_KEY)
            }

        listOfCategories = arrayList?.map {
            it as HelpCategoryEnum
        } ?: listOfCategories

        helpCategoriesAdapter.submitList(listOfCategories)

        if (listOfCategories != null) {
            binding.contentHelpCategories.progressBar.isVisible = false
        }

        startJsonLoaderService()
    }

    private fun loadIfAbsentState() {
        binding.contentHelpCategories.progressBar.isVisible = true
        startJsonLoaderService()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HelpCategoriesFragment()

        private const val LIST_OF_CATEGORIES_KEY = "LIST_OF_CATEGORIES_KEY"
    }
}
