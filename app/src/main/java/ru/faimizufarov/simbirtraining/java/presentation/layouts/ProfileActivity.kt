package ru.faimizufarov.simbirtraining.presentation.layouts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.ActivityProfileBinding

@Suppress("ktlint:standard:no-empty-first-line-in-class-body")
class ProfileActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.bottomNavView.selectedItemId = R.id.action_profile
    }
}
