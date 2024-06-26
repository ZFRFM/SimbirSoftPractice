package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.FragmentNewsBinding
import ru.faimizufarov.simbirtraining.java.data.models.News
import ru.faimizufarov.simbirtraining.java.presentation.ui.holders.BadgeCounterHolder
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.detail_description.DetailDescriptionFragment
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news.adapters.NewsAdapter
import ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news_filter.NewsFilterFragment

class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding

    private val newsAdapter = NewsAdapter(onItemClick = ::updateFeed)

    private val newsViewModel: NewsViewModel by viewModels {
        NewsViewModel.Factory(requireContext())
    }

    private val readNewsIdsStateFlow: MutableStateFlow<List<String>> =
        MutableStateFlow(listOf())

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

        newsViewModel.newsLiveData.observe(viewLifecycleOwner) { news ->
            newsAdapter.submitList(news)
        }

        lifecycleScope.launch {
            readNewsIdsStateFlow.collect {
                // FIXME: still bad, shouldn't normally read directly from livedata,
                //  as well as using lifecycleScope at all
                val availableNews = newsViewModel.newsLiveData.value

                val unreadNews =
                    availableNews?.filter { news: News ->
                        !readNewsIdsStateFlow.value.contains(news.id)
                    } ?: emptyList()

                BadgeCounterHolder.setBadgeCounterEmitValue(unreadNews.size)
            }
        }

        binding.contentNews.recyclerViewNewsFragment.adapter = newsAdapter

        binding.imageViewFilter.setOnClickListener {
            openFilterFragment()
        }
    }

    private fun openFilterFragment() {
        parentFragmentManager.beginTransaction().add(
            R.id.fragmentContainerView,
            NewsFilterFragment.newInstance(),
        ).commit()
    }

    private fun updateFeed(news: News) {
        lifecycleScope.launch {
            readNewsIdsStateFlow.emit(readNewsIdsStateFlow.value + news.id)
        }

        val startDate =
            Instant.fromEpochMilliseconds(news.startDate)
                .toLocalDateTime(TimeZone.currentSystemDefault()).toString()
        val finishDate =
            Instant.fromEpochMilliseconds(news.finishDate)
                .toLocalDateTime(TimeZone.currentSystemDefault()).toString()

        val bundle =
            bundleOf(
                DetailDescriptionFragment.IMAGES_VIEW_NEWS
                    to news.newsImages,
                DetailDescriptionFragment.TEXT_VIEW_NAME
                    to news.nameText,
                DetailDescriptionFragment.TEXT_VIEW_DESCRIPTION
                    to news.descriptionText,
                DetailDescriptionFragment.START_DATE
                    to startDate,
                DetailDescriptionFragment.FINISH_DATE
                    to finishDate,
            )

        setFragmentResult(DetailDescriptionFragment.NEWS_POSITION_RESULT, bundle)
        parentFragmentManager.beginTransaction().add(
            R.id.fragmentContainerView,
            DetailDescriptionFragment.newInstance(),
        ).commit()
    }

    companion object {
        fun newInstance() = NewsFragment()
    }
}
