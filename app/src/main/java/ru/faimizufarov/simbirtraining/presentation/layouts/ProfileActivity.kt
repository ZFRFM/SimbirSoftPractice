package ru.faimizufarov.simbirtraining.presentation.layouts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    lateinit var viewBinding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.bottomNavView.selectedItemId = R.id.action_profile
    }
}