package ru.faimizufarov.simbirtraining.java.presentation.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.ActivityMainBinding
import ru.faimizufarov.simbirtraining.java.presentation.ui.fragments.BadgeCounter
import ru.faimizufarov.simbirtraining.java.presentation.ui.fragments.HelpCategoriesFragment
import ru.faimizufarov.simbirtraining.java.presentation.ui.fragments.NewsFragment
import ru.faimizufarov.simbirtraining.java.presentation.ui.fragments.ProfileFragment
import ru.faimizufarov.simbirtraining.java.presentation.ui.fragments.SearchFragment

@Suppress("ktlint:standard:no-empty-first-line-in-class-body")
class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(viewBinding.root)
        viewBinding.bottomNavView.selectedItemId = R.id.action_help

        viewBinding.bottomNavView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_help -> setCurrentFragment(HelpCategoriesFragment.newInstance())
                R.id.action_profile -> setCurrentFragment(ProfileFragment.newInstance())
                R.id.action_search -> setCurrentFragment(SearchFragment.newInstance())
                R.id.action_news -> setCurrentFragment(NewsFragment.newInstance())
            }
            true
        }

        BadgeCounter.badgeCounter.subscribe(this::updateBadgeCount).let(disposables::add)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    private fun updateBadgeCount(unreadNewsCount: Int) {
        val badge = viewBinding.bottomNavView.getOrCreateBadge(R.id.action_news)
        val isNewsUnread = unreadNewsCount > 0
        if (isNewsUnread) {
            badge.number = unreadNewsCount
        }
        badge.isVisible = isNewsUnread
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, fragment)
            commit()
        }
    }
}
