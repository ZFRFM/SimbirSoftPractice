package ru.faimizufarov.simbirtraining.java.presentation.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.ActivityMainBinding
import ru.faimizufarov.simbirtraining.java.presentation.ui.fragments.HelpCategoriesFragment
import ru.faimizufarov.simbirtraining.java.presentation.ui.fragments.NewsFragment
import ru.faimizufarov.simbirtraining.java.presentation.ui.fragments.ProfileFragment
import ru.faimizufarov.simbirtraining.java.presentation.ui.fragments.SearchFragment
import ru.faimizufarov.simbirtraining.java.presentation.ui.observers.ObserverMainActivity

@Suppress("ktlint:standard:no-empty-first-line-in-class-body")
class MainActivity : AppCompatActivity(), ObserverMainActivity {
    private lateinit var viewBinding: ActivityMainBinding

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
    }

    override fun update(unreadNewsCount: Int) {
        if (unreadNewsCount != 0) {
            viewBinding
                .bottomNavView
                .getOrCreateBadge(R.id.action_news)
                .number = unreadNewsCount

            viewBinding
                .bottomNavView
                .getOrCreateBadge(R.id.action_news)
                .isVisible = true
        } else {
            viewBinding
                .bottomNavView
                .getOrCreateBadge(R.id.action_news)
                .isVisible = false
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, fragment)
            commit()
        }
    }
}
