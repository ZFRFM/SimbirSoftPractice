package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.serialization.json.Json
import ru.faimizufarov.simbirtraining.databinding.FragmentHelpCategoriesBinding
import ru.faimizufarov.simbirtraining.java.data.CategoryResponse
import ru.faimizufarov.simbirtraining.java.data.HelpCategoryEnum
import ru.faimizufarov.simbirtraining.java.presentation.ui.adapters.HelpCategoriesAdapter
import ru.faimizufarov.simbirtraining.java.services.JsonLoaderService

class HelpCategoriesFragment : Fragment() {
    private lateinit var binding: FragmentHelpCategoriesBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var helpCategoriesAdapter: HelpCategoriesAdapter
    private var listOfCategoriesJson = listOf<HelpCategoryEnum>()
    private var fileInString: String? = ""
    private lateinit var mService: JsonLoaderService
    private var mBound: Boolean = false
    private lateinit var fragmentContext: Context
    var progressBarIsVisible = true
    private var onProgressBarIsVisible: ((Boolean) -> Unit)? = null

    private val connection =
        object : ServiceConnection {
            override fun onServiceConnected(
                className: ComponentName,
                service: IBinder,
            ) {
                val binder = service as JsonLoaderService.LocalBinder
                mService = binder.getService()
                mBound = true

                setOnFilterChangedListener { changedFileInString ->
                    fileInString = changedFileInString
                    listOfCategoriesJson = getListOfCategories(fileInString!!)
                    helpCategoriesAdapter.setData(listOfCategoriesJson)
                    progressBarIsVisible = false
                    activity?.runOnUiThread {
                        onProgressBarIsVisible?.invoke(progressBarIsVisible)
                    }
                }
            }

            override fun onServiceDisconnected(arg0: ComponentName) {
                mBound = false
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
        recyclerView = binding.contentHelpCategories.recyclerViewHelpCategories
        fragmentContext = requireContext()
        helpCategoriesAdapter = HelpCategoriesAdapter()
        recyclerView.adapter = helpCategoriesAdapter

        if (savedInstanceState != null) {
            fileInString = savedInstanceState.getString(JSON_KEY)!!
            listOfCategoriesJson =
                if (fileInString == "") {
                    listOfCategoriesJson
                } else {
                    getListOfCategories(fileInString!!)
                }
            helpCategoriesAdapter.setData(listOfCategoriesJson)
            binding.contentHelpCategories.progressBar.visibility = View.GONE
            startJsonLoaderService()
        } else {
            startJsonLoaderService()
            binding.contentHelpCategories.progressBar.visibility = View.VISIBLE
            helpCategoriesAdapter.setData(listOfCategoriesJson)
            recyclerView.adapter = helpCategoriesAdapter
        }

        setOnProgressBarChangedListener { progressBarIsVisible ->
            binding.contentHelpCategories.progressBar.visibility =
                if (progressBarIsVisible) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(JSON_KEY, fileInString)
    }

    override fun onStop() {
        super.onStop()
        activity?.unbindService(connection)
        mBound = false
    }

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

    private fun startJsonLoaderService() {
        val serviceIntent = Intent(fragmentContext, JsonLoaderService::class.java)
        activity?.startService(serviceIntent)
        Intent(fragmentContext, JsonLoaderService::class.java).also { intent ->
            activity?.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    fun setOnFilterChangedListener(listener: ((String) -> Unit)?) {
        mService.onFileInStringChanged = listener
    }

    private fun setOnProgressBarChangedListener(listener: ((Boolean) -> Unit)?) {
        onProgressBarIsVisible = listener
    }

    companion object {
        @JvmStatic
        fun newInstance() = HelpCategoriesFragment()

        const val JSON_KEY = "JSON_KEY"
    }
}
