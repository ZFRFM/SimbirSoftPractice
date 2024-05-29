package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(this)
            .load(R.drawable.avatar_3)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.drawable.baseline_create_24)
            .error(R.drawable.baseline_error_24)
            .into(binding.contentProfileInfo.imageViewFirstAvatar)

        Glide.with(this)
            .load(R.drawable.avatar_2)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.drawable.baseline_create_24)
            .error(R.drawable.baseline_error_24)
            .into(binding.contentProfileInfo.imageViewSecondAvatar)

        Glide.with(this)
            .load(R.drawable.avatar_1)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.drawable.baseline_create_24)
            .error(R.drawable.baseline_error_24)
            .into(binding.contentProfileInfo.imageViewThirdAvatar)

        setFragmentResultListener(
            ProfilePhotoEditDialog.USER_PICTURE_RESULT_KEY,
        ) { key, bundle ->
            val uri =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable(
                        ProfilePhotoEditDialog.USER_PICTURE_KEY,
                        Uri::class.java,
                    )
                } else {
                    bundle.getParcelable(ProfilePhotoEditDialog.USER_PICTURE_KEY)
                }

            binding.contentProfileInfo.imageViewMan.setImageURI(uri)
        }

        setFragmentResultListener(
            ProfilePhotoEditDialog.DELETE_USER_PICTURE_RESULT_KEY,
        ) { key, bundle ->
            if (bundle.containsKey(ProfilePhotoEditDialog.DELETE_USER_PICTURE_FLAG_KEY)) {
                binding.contentProfileInfo.imageViewMan
                    .setImageResource(R.drawable.empty_drawable_foreground)
            }
        }

        setFragmentResultListener(
            ProfilePhotoEditDialog.USER_GALLERY_PICTURE_RESULT_KEY,
        ) { key, bundle ->
            val uri =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable(
                        ProfilePhotoEditDialog.USER_GALLERY_PICTURE_KEY,
                        Uri::class.java,
                    )
                } else {
                    bundle.getParcelable(ProfilePhotoEditDialog.USER_GALLERY_PICTURE_KEY)
                }
            binding.contentProfileInfo.imageViewMan.setImageURI(uri)
        }

        binding.contentProfileInfo.imageViewMan.setOnClickListener {
            val dialog = ProfilePhotoEditDialog.newInstance()
            dialog.show(childFragmentManager, ProfilePhotoEditDialog.TAG)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}
