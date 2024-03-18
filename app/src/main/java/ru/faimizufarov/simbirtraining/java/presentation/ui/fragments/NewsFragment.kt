package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

    lateinit var filters: List<Category>
    lateinit var appliedFilters: List<News>

    val newsAdapter = NewsAdapter()

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

        appliedFilters = listOfNews

        setFragmentResultListener(NewsFilterFragment.APPLIED_FILTERS_RESULT) { key, bundle ->
            filters =
                (
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        bundle.getParcelable(
                            NewsFilterFragment.APPLIED_FILTERS,
                            List::class.java,
                        )
                    } else {
                        bundle.getParcelable(NewsFilterFragment.APPLIED_FILTERS)
                    }
                ) as List<Category>

            filters.filter { !it.checked }.forEach {
                val category = it
                appliedFilters = listOfNews.filter { !it.helpCategory.contains(category) }
            }
        }

        newsAdapter.setData(appliedFilters)
        binding.included.recyclerViewNewsFragment.adapter = newsAdapter

        binding.imageViewFilter.setOnClickListener {
            parentFragmentManager.beginTransaction().add(
                R.id.fragmentContainerView,
                NewsFilterFragment.newInstance(),
            ).commit()
        }
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
                            3,
                            20,
                            10,
                            0,
                            0,
                            0,
                        ),
                    finishDate =
                        LocalDateTime(
                            2024,
                            3,
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
