package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

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
import ru.faimizufarov.simbirtraining.java.data.NewsJsonRepresentation
import ru.faimizufarov.simbirtraining.java.presentation.ui.adapters.NewsAdapter

class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private var appliedFiltersNews = mutableListOf<News>()
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var listOfNewsJson: List<News>

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

        newsAdapter = NewsAdapter(requireContext())
        val fileInString by lazy {
            requireContext()
                .applicationContext
                .assets
                .open("JsonNews")
                .bufferedReader()
                .use { it.readText() }
        }

        listOfNewsJson =
            Json
                .decodeFromString<Array<NewsJsonRepresentation>>(fileInString)
                .map { it ->
                    News(
                        newsImage = it.newsImage,
                        nameText = it.nameText,
                        descriptionText = it.textViewDescription,
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

        NewsFilterHolder.setOnFilterChangedListener { listFilters ->
            for (i in 0 until NewsFilterHolder.getFilterList().size) {
                if (listFilters.contains(
                        Category(
                            NewsFilterHolder.getFilterList()[i].enumValue,
                        ),
                    )
                ) {
                    appliedFiltersNews.addAll(
                        listOfNewsJson.filter {
                            it.helpCategory.contains(NewsFilterHolder.getFilterList()[i])
                        },
                    )
                }
            }
            updateAdapter(appliedFiltersNews)
        }

        updateAdapter(listOfNewsJson)

        binding.imageViewFilter.setOnClickListener {
            parentFragmentManager.beginTransaction().add(
                R.id.fragmentContainerView,
                NewsFilterFragment.newInstance(),
            ).commit()
        }
    }

    private fun updateAdapter(list: List<News>) {
        newsAdapter.setData(list.toSet().toList())
        binding.contentNews.recyclerViewNewsFragment.adapter = newsAdapter

        newsAdapter.onItemClick = { news: News ->
            val startDate = news.startDate.toString()
            val finishDate = news.finishDate.toString()

            val bundle =
                bundleOf(
                    DetailDescriptionFragment.IMAGE_VIEW_NEWS
                        to news.newsImage,
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

        appliedFiltersNews = mutableListOf()
    }

    companion object {
        fun newInstance() = NewsFragment()
    }
}
