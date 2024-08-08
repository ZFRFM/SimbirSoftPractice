package ru.faimizufarov.news

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.faimizufarov.core.theme.HelpTheme
import ru.faimizufarov.domain.models.News
import ru.faimizufarov.news.databinding.FragmentNewsComposeBinding
import ru.faimizufarov.news.di.NewsComponentProvider
import ru.faimizufarov.news.models.toNews
import javax.inject.Inject

class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsComposeBinding
    private var newsNavigator: NewsNavigator? = null

    @Inject
    lateinit var newsViewModelFactory: NewsViewModelFactory
    private lateinit var newsViewModel: NewsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NewsNavigator) newsNavigator = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as NewsComponentProvider)
            .provideNewsComponent()
            .injectNewsFragment(this)
        newsViewModel =
            ViewModelProvider(
                this,
                newsViewModelFactory,
            )[NewsViewModel::class]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNewsComposeBinding.inflate(layoutInflater, container, false)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                HelpTheme {
                    NewsScreen(
                        clickFilter = { openFilterFragment() },
                        clickItem = { updateFeed(it.toNews()) },
                    )
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        newsViewModel.collectReadNewsIds()
    }

    private fun openFilterFragment() {
        newsNavigator?.navigateToFilterFragment()
    }

    private fun updateFeed(news: News) {
        newsViewModel.emitReadNewsIds(news)

        val startDate =
            Instant.fromEpochMilliseconds(news.startDate)
                .toLocalDateTime(TimeZone.currentSystemDefault()).toString()
        val finishDate =
            Instant.fromEpochMilliseconds(news.finishDate)
                .toLocalDateTime(TimeZone.currentSystemDefault()).toString()

        val bundle =
            bundleOf(
                IMAGES_VIEW_NEWS to news.newsImages,
                TEXT_VIEW_NAME to news.nameText,
                TEXT_VIEW_DESCRIPTION to news.descriptionText,
                START_DATE to startDate,
                FINISH_DATE to finishDate,
            )

        setFragmentResult(NEWS_POSITION_RESULT, bundle)

        newsNavigator?.navigateToDetailDescriptionFragment()
    }

    override fun onDetach() {
        super.onDetach()
        newsNavigator = null
    }

    companion object {
        const val IMAGES_VIEW_NEWS = "IMAGES_VIEW_NEWS"
        const val TEXT_VIEW_NAME = "TEXT_VIEW_NAME"
        const val TEXT_VIEW_DESCRIPTION = "TEXT_VIEW_DESCRIPTION"
        const val START_DATE = "START_DATE"
        const val FINISH_DATE = "FINISH_DATE"
        const val NEWS_POSITION_RESULT = "NEWS_POSITION_RESULT"

        fun newInstance() = NewsFragment()
    }
}
