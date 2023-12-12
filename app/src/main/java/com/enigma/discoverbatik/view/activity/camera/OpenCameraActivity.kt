package com.enigma.discoverbatik.view.activity.camera

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.enigma.discoverbatik.databinding.ActivityOpenCameraBinding
import com.enigma.discoverbatik.utils.CommonUtils

class OpenCameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOpenCameraBinding

    //    Untuk save Uri dari image yang mengambil image dari gallery
    private var currentImageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpenCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }

            btnCamera.setOnClickListener {
                startActivity(Intent(this@OpenCameraActivity, CameraActivity::class.java))
            }

            btnGallery.setOnClickListener {
                startGallery()
            }
        }

        uriImageFromCameraX()


    }

//    Start Intent Gallery START ⭐

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
            if (uri != null) {
                currentImageUri = uri
                showImage()
            } else {
                Log.d("Image Uri", "Photo Picker : No Media Selected")
            }
        }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image Uri", "Show Uri : $it")
            binding.contentImage.setImageURI(it)

        }
    }

    private fun uriImageFromCameraX() {
        val receivedIntent = intent
        if (receivedIntent != null && receivedIntent.hasExtra("IMAGE_URI")) {
            val imageUriString = receivedIntent.getStringExtra("IMAGE_URI")
            val imageUri: Uri = Uri.parse(imageUriString)
            currentImageUri = imageUri
            binding.contentImage.setImageURI(imageUri)
            CommonUtils.showToast(this@OpenCameraActivity, "Image Uri From Camera X: $imageUri")
        }
    }

    //    Start Intent Gallery END 👋


}