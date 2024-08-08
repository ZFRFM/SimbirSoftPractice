package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.detail_description

import android.net.Uri
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
import ru.faimizufarov.news.NewsFragment
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.FragmentDetailDescriptionBinding

class DetailDescriptionFragment : Fragment() {
    private lateinit var binding: FragmentDetailDescriptionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            FragmentDetailDescriptionBinding
                .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener(NewsFragment.NEWS_POSITION_RESULT) { _, bundle ->

            val startDate =
                LocalDateTime
                    .parse(bundle.getString(NewsFragment.START_DATE) ?: "")

            val finishDate =
                LocalDateTime
                    .parse(bundle.getString(NewsFragment.FINISH_DATE) ?: "")

            val finishDay =
                LocalDateTime.parse(
                    bundle.getString(NewsFragment.FINISH_DATE) ?: "",
                ).date.toEpochDays()

            val today = Clock.System.todayIn(TimeZone.currentSystemDefault()).toEpochDays()

            with(binding.contentDetailDescription) {
                val imageUrlList = bundle.getStringArrayList(NewsFragment.IMAGES_VIEW_NEWS)
                val imageUrl = imageUrlList?.first().toString()

                if (!imageUrl.startsWith("images/news")) {
                    Glide
                        .with(requireContext())
                        .load(imageUrl)
                        .into(binding.contentDetailDescription.imageViewFirstPicture)
                } else {
                    Glide
                        .with(requireContext())
                        .load(Uri.parse("file:///android_asset/$imageUrl"))
                        .into(binding.contentDetailDescription.imageViewFirstPicture)
                }

                textViewNews.text = bundle.getString(NewsFragment.TEXT_VIEW_NAME)

                textViewDescTop.text = bundle.getString(NewsFragment.TEXT_VIEW_DESCRIPTION)

                textViewRemainingTime.text =
                    if (finishDay - today >= 0) {
                        getString(
                            R.string.news_remaining_time_with_args,
                            finishDay - today,
                            startDate.dayOfMonth,
                            startDate.monthNumber,
                            finishDate.dayOfMonth,
                            finishDate.monthNumber,
                        )
                    } else {
                        getString(R.string.news_event_finished)
                    }
            }
        }

        binding.imageViewBack.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
    }

    companion object {
        fun newInstance(): DetailDescriptionFragment {
            return DetailDescriptionFragment()
        }
    }
}
