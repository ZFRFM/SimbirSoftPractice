package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.DialogFragmentProfilePhotoEditBinding
import java.io.File

class ProfilePhotoEditDialog(imageView: ImageView) : DialogFragment() {
    @Suppress("ktlint:standard:property-naming")
    private lateinit var binding: DialogFragmentProfilePhotoEditBinding

    private val imageView by lazy { imageView }

    var tempImageUri: Uri? = null
    lateinit var cameraLauncher: ActivityResultLauncher<Uri>
    lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private var tempImageFilePath = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DialogFragmentProfilePhotoEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
                if (success) {
                    imageView.setImageURI(tempImageUri)
                }
                this.dismiss()
            }

        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    takeAPhoto()
                } else {
                    Toast
                        .makeText(
                            requireContext(),
                            "App is need permission for this option",
                            Toast.LENGTH_SHORT,
                        )
                        .show()
                }
            }

        binding.linearLayoutTakeAPhoto.setOnClickListener {
            if (!checkCameraPermission()) {
                requestPermissionLauncher.launch("android.permission.CAMERA")
            } else {
                takeAPhoto()
            }
        }

        binding.linearLayoutDelete.setOnClickListener {
            parentFragment
                ?.requireView()
                ?.requireViewById<ImageView>(imageView.id)
                ?.setImageResource(R.drawable.empty_drawable_foreground)

            this.dismiss()
        }
    }

    private fun createImageFile(): File {
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("temp_image", ".jpg", storageDir)
    }

    private fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), "android.permission.CAMERA") == PackageManager.PERMISSION_GRANTED
    }

    private fun takeAPhoto() {
        tempImageUri =
            FileProvider.getUriForFile(
                requireContext(),
                "ru.faimizufarov.simbirtraining.provider",
                createImageFile().also {
                    tempImageFilePath = it.absolutePath
                },
            )
        cameraLauncher.launch(tempImageUri)
    }

    companion object {
        const val TAG = "ProfilePhotoEditDialog"

        @JvmStatic
        fun newInstance(imageView: ImageView): ProfilePhotoEditDialog = ProfilePhotoEditDialog(imageView)
    }
}
