package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import ru.faimizufarov.simbirtraining.databinding.FragmentHelpCategoriesBinding
import ru.faimizufarov.simbirtraining.java.data.HelpCategoryEnum
import ru.faimizufarov.simbirtraining.java.presentation.ui.adapters.HelpCategoriesAdapter
import ru.faimizufarov.simbirtraining.java.services.JsonLoaderService

class HelpCategoriesFragment : Fragment() {
    private lateinit var binding: FragmentHelpCategoriesBinding
    private val helpCategoriesAdapter = HelpCategoriesAdapter()
    private var listOfCategories = listOf<HelpCategoryEnum>()
    private lateinit var jsonLoaderService: JsonLoaderService
    private var isProgressBarVisible = true
    private var onProgressBarIsVisible: ((Boolean) -> Unit)? = null

    private val connection =
        object : ServiceConnection {
            override fun onServiceConnected(
                className: ComponentName,
                service: IBinder,
            ) {
                val binder = service as JsonLoaderService.LocalBinder
                jsonLoaderService = binder.getService()

                setOnFilterChangedListener { changedListOfCategory ->
                    listOfCategories = jsonLoaderService.getListOfCategories()
                    helpCategoriesAdapter.setData(listOfCategories)
                    isProgressBarVisible = false
                    activity?.runOnUiThread {
                        onProgressBarIsVisible?.invoke(isProgressBarVisible)
                    }
                }
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                TODO("Not yet implemented")
            }
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

        if (savedInstanceState != null) {
            val arrayList =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    savedInstanceState.getParcelable(
                        LIST_OF_CATEGORIES_KEY,
                        ArrayList::class.java,
                    )
                } else {
                    savedInstanceState
                        .getParcelable(LIST_OF_CATEGORIES_KEY)
                }
            listOfCategories = arrayList?.map {
                it as HelpCategoryEnum
            } ?: listOfCategories

            helpCategoriesAdapter.setData(listOfCategories)
            binding.contentHelpCategories.progressBar.visibility = View.GONE
            startJsonLoaderService()
        } else {
            startJsonLoaderService()
            binding.contentHelpCategories.progressBar.visibility = View.VISIBLE
            helpCategoriesAdapter.setData(listOfCategories)
        }

        setOnProgressBarChangedListener { progressBarIsVisible ->
            binding.contentHelpCategories.progressBar.isVisible = progressBarIsVisible
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val arrayList = ArrayList<HelpCategoryEnum>()
        listOfCategories.forEach {
            arrayList.add(it)
        }
        outState.putParcelableArrayList(LIST_OF_CATEGORIES_KEY, arrayList)
    }

    override fun onStop() {
        super.onStop()
        activity?.unbindService(connection)
    }

    private fun startJsonLoaderService() {
        val serviceIntent = Intent(requireContext(), JsonLoaderService::class.java)
        activity?.startService(serviceIntent)
        Intent(requireContext(), JsonLoaderService::class.java).also { intent ->
            activity?.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    fun setOnFilterChangedListener(listener: ((List<HelpCategoryEnum>) -> Unit)?) {
        jsonLoaderService.onListOfCategoryChanged = listener
    }

    private fun setOnProgressBarChangedListener(listener: ((Boolean) -> Unit)?) {
        onProgressBarIsVisible = listener
    }

    companion object {
        @JvmStatic
        fun newInstance() = HelpCategoriesFragment()

        private const val LIST_OF_CATEGORIES_KEY = "LIST_OF_CATEGORIES_KEY"
    }
}
