package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.FragmentDetailDescBinding
import ru.faimizufarov.simbirtraining.java.data.News

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
            val news =
                if (key == NEWS_POSITION && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable(NEWS_POSITION, News::class.java)
                } else {
                    bundle.getParcelable(NEWS_POSITION)
                }
            binding.imageViewBack.setImageResource(news?.imageViewNews ?: R.drawable.empty_drawable_foreground)
            binding.included.textViewFundName.setText(news?.textViewName!!)
        }
    }

    companion object {
        const val NEWS_POSITION = "NEWS_POSITION"
        const val NEWS_POSITION_RESULT = "NEWS_POSITION_RESULT"

        fun newInstance(): DetailDescFragment {
            return DetailDescFragment()
        }
    }
}
