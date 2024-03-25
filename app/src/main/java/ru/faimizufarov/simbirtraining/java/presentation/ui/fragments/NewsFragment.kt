package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import kotlinx.datetime.LocalDateTime
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.FragmentNewsBinding
import ru.faimizufarov.simbirtraining.java.data.Category
import ru.faimizufarov.simbirtraining.java.data.HelpCategoryEnum
import ru.faimizufarov.simbirtraining.java.data.News
import ru.faimizufarov.simbirtraining.java.presentation.ui.adapters.NewsAdapter

class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding

    private var listAppliedFilters: MutableList<Category>? = mutableListOf()
    private var appliedFiltersNews = mutableListOf<News>()

    private val newsAdapter = NewsAdapter()

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

        setFragmentResultListener(NewsFilterFragment.APPLIED_FILTERS_RESULT) { key, bundle ->

            listAppliedFilters =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable(
                        NewsFilterFragment.APPLIED_FILTERS,
                        MutableList::class.java,
                    )
                } else {
                    bundle.getParcelable(NewsFilterFragment.APPLIED_FILTERS)
                } as MutableList<Category>

            for (i in 0 until NewsFilterHolder.listFilters.size) {
                if (listAppliedFilters!!.contains(
                        Category(
                            NewsFilterHolder.listFilters[i].enumValue,
                        ),
                    )
                ) {
                    appliedFiltersNews.addAll(
                        listOfNews.filter {
                            it.helpCategory.contains(NewsFilterHolder.listFilters[i])
                        },
                    )
                }
            }

            updateAdapter(appliedFiltersNews)
        }

        updateAdapter(listOfNews)
    }

    private fun updateAdapter(list: List<News>) {
        newsAdapter.setData(list.toSet().toList())
        binding.included.recyclerViewNewsFragment.adapter = newsAdapter

        binding.imageViewFilter.setOnClickListener {
            parentFragmentManager.beginTransaction().add(
                R.id.fragmentContainerView,
                NewsFilterFragment.newInstance(),
            ).commit()
        }

        newsAdapter.onItemClick = { news: News ->
            val startDate = news.startDate.toString()
            val finishDate = news.finishDate.toString()

            val bundle =
                bundleOf(
                    DetailDescFragment.IMAGE_VIEW_NEWS to news.imageViewNews,
                    DetailDescFragment.TEXT_VIEW_NAME to news.textViewName,
                    DetailDescFragment.TEXT_VIEW_DESCRIPTION to news.textViewDescription,
                    DetailDescFragment.TEXT_VIEW_REMAINING_TIME to news.textViewRemainingTime,
                    DetailDescFragment.START_DATE to startDate,
                    DetailDescFragment.FINISH_DATE to finishDate,
                )

            setFragmentResult(DetailDescFragment.NEWS_POSITION_RESULT, bundle)
            parentFragmentManager.beginTransaction().replace(
                R.id.fragmentContainerView,
                DetailDescFragment.newInstance(),
            ).commit()
        }

        appliedFiltersNews = mutableListOf()
    }

    companion object {
        fun newInstance() = NewsFragment()

        val listOfNews =
            listOf(
                News(
                    imageViewNews = R.drawable.news_1_image,
                    textViewName = R.string.news_1_name,
                    textViewDescription = R.string.news_1_description,
                    textViewRemainingTime = R.string.news_remaining_time,
                    helpCategory = listOf(Category(HelpCategoryEnum.CHILDREN, true)),
                    startDate =
                        LocalDateTime(
                            2024,
                            3,
                            1,
                            10,
                            0,
                            0,
                            0,
                        ),
                    finishDate =
                        LocalDateTime(
                            2024,
                            3,
                            29,
                            20,
                            0,
                            0,
                            0,
                        ),
                ),
                News(
                    imageViewNews = R.drawable.news_2_image,
                    textViewName = R.string.news_2_name,
                    textViewDescription = R.string.news_1_description,
                    textViewRemainingTime = R.string.news_remaining_time,
                    helpCategory =
                        listOf(
                            Category(HelpCategoryEnum.CHILDREN, true),
                            Category(HelpCategoryEnum.ADULTS, true),
                        ),
                    startDate =
                        LocalDateTime(
                            2024,
                            4,
                            20,
                            10,
                            0,
                            0,
                            0,
                        ),
                    finishDate =
                        LocalDateTime(
                            2024,
                            4,
                            20,
                            20,
                            0,
                            0,
                            0,
                        ),
                ),
            )
    }
}
