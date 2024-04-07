package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.FragmentNewsBinding
import ru.faimizufarov.simbirtraining.java.data.Category
import ru.faimizufarov.simbirtraining.java.data.HelpCategoryEnum
import ru.faimizufarov.simbirtraining.java.data.News
import ru.faimizufarov.simbirtraining.java.data.NewsResponse
import ru.faimizufarov.simbirtraining.java.presentation.ui.adapters.NewsAdapter
import java.util.concurrent.Executors

class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private var appliedFiltersNews = mutableListOf<News>()
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var fragmentContext: Context
    private var listOfNewsJson = listOf<News>()
    private var fileInString = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        fragmentContext = requireContext()
        newsAdapter = getAdapterInstallation()

        if (savedInstanceState != null) {
            fileInString = savedInstanceState.getString(JSON_KEY) ?: ""
            listOfNewsJson =
                if (fileInString == "") {
                    listOfNewsJson
                } else {
                    getNewsListFromJson(fileInString)
                }
            executeJsonReading()
            newsAdapter.setData(listOfNewsJson)
        } else {
            executeJsonReading()
        }

        NewsFilterHolder.setOnFilterChangedListener { listFilters ->
            NewsFilterHolder.getFilterList().forEach { filteredCategory ->
                if (listFilters.contains(filteredCategory)) {
                    val filteredNews = listOfNewsJson.filterByCategory(filteredCategory)
                    appliedFiltersNews.addAll(filteredNews)
                }
            }
            updateAdapter(appliedFiltersNews)
        }

        updateAdapter(listOfNewsJson)

        binding.imageViewFilter.setOnClickListener {
            openFilterFragment()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(JSON_KEY, fileInString)
    }

    private fun executeJsonReading() {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            Thread.sleep(5000)
            fileInString = getNewsJson()
            listOfNewsJson = getNewsListFromJson(fileInString)
            newsAdapter.setData(listOfNewsJson)
            executor.shutdown()
        }
    }

    private fun getNewsJson() =
        fragmentContext
            .applicationContext
            .assets
            .open("news_list.json")
            .bufferedReader()
            .use { it.readText() }

    private fun getNewsListFromJson(json: String) =
        Json
            .decodeFromString<Array<NewsResponse>>(json)
            .map { it ->
                News(
                    id = it.id,
                    newsImageUrl = it.newsImage,
                    nameText = it.nameText,
                    descriptionText = it.descriptionText,
                    remainingTimeText = R.string.news_remaining_time,
                    helpCategory =
                        it.helpCategory.map {
                            Category(
                                enumValue =
                                    when (it.id) {
                                        0 -> HelpCategoryEnum.CHILDREN
                                        1 -> HelpCategoryEnum.ADULTS
                                        2 -> HelpCategoryEnum.ELDERLY
                                        3 -> HelpCategoryEnum.ANIMALS
                                        else -> HelpCategoryEnum.EVENTS
                                    },
                                checked = it.checked,
                            )
                        },
                    startDate = it.startDate.toLocalDateTime(),
                    finishDate = it.finishDate.toLocalDateTime(),
                )
            }

    private fun List<News>.filterByCategory(category: Category) =
        filter { news ->
            news.helpCategory.any { it == category }
        }

    private fun openFilterFragment() {
        parentFragmentManager.beginTransaction().add(
            R.id.fragmentContainerView,
            NewsFilterFragment.newInstance(),
        ).commit()
    }

    private fun getAdapterInstallation(): NewsAdapter {
        return NewsAdapter(fragmentContext) { news: News ->
            val startDate = news.startDate.toString()
            val finishDate = news.finishDate.toString()

            val bundle =
                bundleOf(
                    DetailDescriptionFragment.IMAGE_VIEW_NEWS
                        to news.newsImageUrl,
                    DetailDescriptionFragment.TEXT_VIEW_NAME
                        to news.nameText,
                    DetailDescriptionFragment.TEXT_VIEW_DESCRIPTION
                        to news.descriptionText,
                    DetailDescriptionFragment.TEXT_VIEW_REMAINING_TIME
                        to news.remainingTimeText,
                    DetailDescriptionFragment.START_DATE
                        to startDate,
                    DetailDescriptionFragment.FINISH_DATE
                        to finishDate,
                )

            setFragmentResult(DetailDescriptionFragment.NEWS_POSITION_RESULT, bundle)
            parentFragmentManager.beginTransaction().replace(
                R.id.fragmentContainerView,
                DetailDescriptionFragment.newInstance(),
            ).commit()
        }
    }

    private fun updateAdapter(list: List<News>) {
        newsAdapter.setData(list.toSet().toList())
        binding.contentNews.recyclerViewNewsFragment.adapter = newsAdapter
        appliedFiltersNews = mutableListOf()
    }

    companion object {
        fun newInstance() = NewsFragment()

        const val JSON_KEY = "JSON_KEY"
    }
}
