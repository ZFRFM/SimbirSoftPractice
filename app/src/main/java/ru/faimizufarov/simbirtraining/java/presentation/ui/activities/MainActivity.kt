package ru.faimizufarov.simbirtraining.java.presentation.ui.activities

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.ActivityMainBinding
import ru.faimizufarov.simbirtraining.java.presentation.ui.fragments.HelpCategoriesFragment
import ru.faimizufarov.simbirtraining.java.presentation.ui.fragments.NewsFragment
import ru.faimizufarov.simbirtraining.java.presentation.ui.fragments.ProfileFragment
import ru.faimizufarov.simbirtraining.java.presentation.ui.fragments.SearchFragment

@Suppress("ktlint:standard:no-empty-first-line-in-class-body")
class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(viewBinding.root)
        viewBinding.bottomNavView.selectedItemId = R.id.action_help

        viewBinding.bottomNavView.setOnItemSelectedListener(
            object : NavigationBarView.OnItemSelectedListener {
                override fun onNavigationItemSelected(p0: MenuItem): Boolean {
                    when (p0.itemId) {
                        R.id.action_help -> setCurrentFragment(HelpCategoriesFragment.newInstance())
                        R.id.action_profile -> setCurrentFragment(ProfileFragment.newInstance())
                        R.id.action_search -> setCurrentFragment(SearchFragment.newInstance())
                        R.id.action_news -> setCurrentFragment(NewsFragment.newInstance())
                    }
                    return true
                }
            },
        )
    }

    override fun onSaveInstanceState(
        outState: Bundle,
        outPersistentState: PersistableBundle,
    ) {
        super.onSaveInstanceState(outState, outPersistentState)
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, fragment)
            commit()
        }
    }
}
