package ru.faimizufarov.simbirtraining.java.presentation.ui.fragments

import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import ru.faimizufarov.simbirtraining.R
import ru.faimizufarov.simbirtraining.databinding.DialogFragmentProfilePhotoEditBinding
import java.io.File

class ProfilePhotoEditDialog : DialogFragment() {
    @Suppress("ktlint:standard:property-naming")
    private val REQUEST_PERMISSION = 100

    private lateinit var binding: DialogFragmentProfilePhotoEditBinding

    private val imageView: ImageView by lazy {
        parentFragment?.view?.findViewById(R.id.imageViewMan) ?: ImageView(null)
    }

    private var tempImageUri: Uri? = null
    private var tempImageFilePath = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DialogFragmentProfilePhotoEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val cameraLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
                if (success) {
                    imageView.setImageURI(tempImageUri)
                }
            }

        binding.linearLayoutTakeAPhoto.setOnClickListener {
            if (!checkCameraPermission()) {
                checkCameraPermission()
            } else {
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
        }

        binding.linearLayoutDelete.setOnClickListener {
            parentFragment?.view?.findViewById<ImageView>(R.id.imageViewMan)?.setImageResource(R.drawable.empty_drawable_foreground)
            parentFragmentManager.beginTransaction().remove(this).commit()
        }
    }

    private fun createImageFile(): File {
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("temp_image", ".jpg", storageDir)
    }

    private fun checkCameraPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(requireContext(), "android.permission.CAMERA")
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireContext() as Activity,
                arrayOf("android.permission.CAMERA"),
                REQUEST_PERMISSION,
            )
            return false
        }
        return true
    }

    companion object {
        const val TAG = "ProfilePhotoEditDialog"
    }
}
