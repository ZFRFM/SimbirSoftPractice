package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.FragmentNewsBinding
import ru.faimizufarov.simbirtraining.java.data.Category
import ru.faimizufarov.simbirtraining.java.data.News
import ru.faimizufarov.simbirtraining.java.presentation.ui.activities.MainActivity
import ru.faimizufarov.simbirtraining.java.presentation.ui.adapters.NewsAdapter
import ru.faimizufarov.simbirtraining.java.presentation.ui.observers.ObservableNewsFragment
import java.util.concurrent.Executors

class NewsFragment() : Fragment(), ObservableNewsFragment {
    private lateinit var binding: FragmentNewsBinding

    private val newsAdapter = NewsAdapter(onItemClick = ::updateFeed)
    private var appliedFiltersNews = mutableListOf<News>()

    private val disposables = CompositeDisposable()
    private val unreadNewsCountSubject = PublishSubject.create<Int>()

    /**
     *
     * Установил lateinit var на observer, т.к. если получать тут activity, то может вылетать
     * исключение из-за того, что фрагмент не успевает прикрепиться к активности. Присвоил
     * значение в onViewCreated, там activity уже точно есть
     *
     * */

    override lateinit var observer: MainActivity

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
        observer = activity as MainActivity

        if (savedInstanceState != null) {
            getFromSavedInstanceState(savedInstanceState)
        } else {
            loadListOfNews()
        }

        val listOfReadNewsIds = mutableListOf<Int>()

        updateBadgeCount(listOfReadNewsIds)

        listenNewsFilterChanged(listOfReadNewsIds)

        updateAdapter(NewsListHolder.getNewsList())

        binding.imageViewFilter.setOnClickListener {
            openFilterFragment()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val arrayList = ArrayList<News>()
        NewsListHolder.getNewsList().forEach {
            arrayList.add(it)
        }
        outState.putParcelableArrayList(LIST_OF_NEWS, arrayList)
    }

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }

    private fun getFromSavedInstanceState(savedInstanceState: Bundle) {
        val newsArray =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                savedInstanceState.getParcelable(LIST_OF_NEWS, ArrayList::class.java)
            } else {
                savedInstanceState.getParcelable(LIST_OF_NEWS)
            } ?: return
        NewsListHolder.setNewsList(newsArray.filterIsInstance<News>())

        newsAdapter.submitList(NewsListHolder.getNewsList())
    }

    private fun loadListOfNews() {
        val executor = Executors.newSingleThreadExecutor()
        val fragmentContext = requireContext()
        executor.execute {
            Thread.sleep(5000)
            val newsJsonInString = NewsListHolder.getNewsJson(fragmentContext)
            NewsListHolder.setNewsList(NewsListHolder.getNewsListFromJson(newsJsonInString))
            sendUpdateBadgeCountEvent(NewsListHolder.getNewsList().size)
            newsAdapter.submitList(NewsListHolder.getNewsList())
            executor.shutdown()
        }
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

    private fun updateFeed(news: News) {
        unreadNewsCountSubject.onNext(news.id)

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
        parentFragmentManager.beginTransaction().add(
            R.id.fragmentContainerView,
            DetailDescriptionFragment.newInstance(),
        ).commit()
    }

    private fun updateAdapter(list: List<News>) {
        newsAdapter.submitList(list.toSet().toList())
        binding.contentNews.recyclerViewNewsFragment.adapter = newsAdapter
        appliedFiltersNews = mutableListOf()
    }

    private fun updateBadgeCount(listOfReadNewsIds: MutableList<Int>) {
        disposables.add(
            unreadNewsCountSubject.subscribe { id ->
                listOfReadNewsIds.add(id)
                val unreadNews =
                    if (appliedFiltersNews.isEmpty()) {
                        (
                            NewsListHolder.getNewsList().filter { news: News ->
                                !listOfReadNewsIds.contains(news.id)
                            }
                        )
                    } else {
                        appliedFiltersNews.filter { news: News ->
                            !listOfReadNewsIds.contains(news.id)
                        }
                    }
                sendUpdateBadgeCountEvent(unreadNews.size)
            },
        )
    }

    private fun listenNewsFilterChanged(listOfReadNewsIds: MutableList<Int>) {
        NewsFilterHolder.setOnFilterChangedListener { listFilters ->
            NewsFilterHolder.getFilterList().forEach { filteredCategory ->
                if (listFilters.contains(filteredCategory)) {
                    val filteredNews =
                        NewsListHolder
                            .getNewsList()
                            .filterByCategory(filteredCategory)
                    appliedFiltersNews.addAll(filteredNews)
                }
            }
            val badgeUpdatedCount =
                appliedFiltersNews.toSet().filter { news: News ->
                    !listOfReadNewsIds.contains(news.id)
                }.size
            sendUpdateBadgeCountEvent(badgeUpdatedCount)
            updateAdapter(appliedFiltersNews)
        }
    }

    companion object {
        fun newInstance() = NewsFragment()

        private const val LIST_OF_NEWS = "LIST_OF_NEWS"
    }
}
