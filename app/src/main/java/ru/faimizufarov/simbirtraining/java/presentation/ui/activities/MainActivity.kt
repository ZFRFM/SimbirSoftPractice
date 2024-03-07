package ru.faimizufarov.simbirtraining.java.presentation.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.ActivityMainBinding

@Suppress("ktlint:standard:no-empty-first-line-in-class-body")
class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.bottomNavView.selectedItemId = R.id.action_profile
    }
}
