package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.bumptech.glide.Glide
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.FragmentDetailDescBinding

class DetailDescFragment : Fragment() {
    private lateinit var binding: FragmentDetailDescBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentDetailDescBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener(NEWS_POSITION_RESULT) { key, bundle ->
            val startDate = LocalDateTime.parse(bundle.getString(START_DATE) ?: "")
            val finishDate = LocalDateTime.parse(bundle.getString(FINISH_DATE) ?: "")
            val finishDay =
                LocalDateTime.parse(
                    bundle.getString(FINISH_DATE) ?: "",
                ).dayOfYear
            val today = Clock.System.todayIn(TimeZone.currentSystemDefault()).dayOfYear
            with(binding.included) {
                val imageUrl = bundle.getString(IMAGE_VIEW_NEWS)
                Glide.with(requireContext()).load(imageUrl)
                    .into(binding.included.imageViewFirstPicture)
                textViewNews.setText(bundle.getString(TEXT_VIEW_NAME))
                textViewDescTop.setText(bundle.getString(TEXT_VIEW_DESCRIPTION))
                textViewRemainingTime.setText(
                    getString(
                        R.string.news_remaining_time,
                        finishDay - today,
                        startDate.dayOfMonth,
                        startDate.monthNumber,
                        finishDate.dayOfMonth,
                        finishDate.monthNumber,
                    ),
                )
            }
        }
    }

    companion object {
        const val IMAGE_VIEW_NEWS = "IMAGE_VIEW_NEWS"
        const val TEXT_VIEW_NAME = "TEXT_VIEW_NAME"
        const val TEXT_VIEW_DESCRIPTION = "TEXT_VIEW_DESCRIPTION"
        const val TEXT_VIEW_REMAINING_TIME = "TEXT_VIEW_REMAINING_TIME"
        const val START_DATE = "START_DATE"
        const val FINISH_DATE = "FINISH_DATE"
        const val NEWS_POSITION_RESULT = "NEWS_POSITION_RESULT"

        fun newInstance(): DetailDescFragment {
            return DetailDescFragment()
        }
    }
}
