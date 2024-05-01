package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.FragmentNewsBinding
import ru.faimizufarov.simbirtraining.java.data.Category
import ru.faimizufarov.simbirtraining.java.data.News
import ru.faimizufarov.simbirtraining.java.presentation.ui.adapters.NewsAdapter
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class NewsFragment() : Fragment() {
    private lateinit var binding: FragmentNewsBinding

    private val newsAdapter = NewsAdapter(onItemClick = ::updateFeed)
    private var appliedFiltersNews = mutableListOf<News>()

    private val unreadNewsCountSubject = PublishSubject.create<Int>()
    private val disposables = CompositeDisposable()

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

        if (savedInstanceState != null) {
            getFromSavedInstanceState(savedInstanceState)
        } else {
            loadListOfNews()
        }

        val listOfReadNewsIds = mutableListOf<Int>()

        unreadNewsCountSubject.subscribe { id ->
            listOfReadNewsIds.add(id)

            val unreadNews =
                if (appliedFiltersNews.isEmpty()) {
                    NewsListHolder.getNewsList().filter { news: News ->
                        !listOfReadNewsIds.contains(news.id)
                    }
                } else {
                    appliedFiltersNews.filter { news: News ->
                        !listOfReadNewsIds.contains(news.id)
                    }
                }

            BadgeCounter.badgeCounter.onNext(unreadNews.size)
        }.let(disposables::add)

        NewsFilterHolder.setOnFilterChangedListener { listFilters ->
            val localFiltersList = NewsFilterHolder.getFilterList()
            localFiltersList.forEach { filteredCategory ->
                if (listFilters.contains(filteredCategory)) {
                    val filteredNews =
                        NewsListHolder.getNewsList().filterByCategory(filteredCategory)
                    appliedFiltersNews.addAll(filteredNews)
                }
            }
            val badgeUpdatedCount =
                appliedFiltersNews.toSet().filter { news: News ->
                    !listOfReadNewsIds.contains(news.id)
                }.size
            BadgeCounter.badgeCounter.onNext(badgeUpdatedCount)
            updateAdapter(appliedFiltersNews)
        }

        updateAdapter(NewsListHolder.getNewsList())

        binding.imageViewFilter.setOnClickListener {
            openFilterFragment()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        putInSavedInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
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

    private fun putInSavedInstanceState(outState: Bundle) {
        val arrayList = ArrayList<News>()
        NewsListHolder.getNewsList().forEach {
            arrayList.add(it)
        }
        outState.putParcelableArrayList(LIST_OF_NEWS, arrayList)
    }

    private fun loadListOfNews() {
        val executor = Executors.newSingleThreadExecutor()
        val fragmentContext = requireContext()

        val jsonObservable =
            Observable.create { emitter ->
                val newsJsonInString = NewsListHolder.getNewsJson(fragmentContext)
                emitter.onNext(newsJsonInString)
            }.delay(5000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())

        executor.execute {
            jsonObservable.subscribe { json ->
                NewsListHolder.setNewsList(NewsListHolder.getNewsListFromJson(json))
                val localNewsListKeeper = NewsListHolder.getNewsList()
                BadgeCounter.badgeCounter.onNext(localNewsListKeeper.size)
                newsAdapter.submitList(localNewsListKeeper)
            }.let(disposables::add)
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

    companion object {
        fun newInstance() = NewsFragment()

        private const val LIST_OF_NEWS = "LIST_OF_NEWS"
    }
}
