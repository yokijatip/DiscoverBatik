package com.enigma.discoverbatik.view.activity.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.enigma.discoverbatik.databinding.ActivityOpenCameraBinding
import com.enigma.discoverbatik.utils.CommonUtils
import com.enigma.discoverbatik.view.activity.camera.CameraActivity.Companion.CAMERAX_RESULT

class OpenCameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOpenCameraBinding

    //    Untuk save Uri dari image yang mengambil image dari gallery
    private var currentImageUri: Uri? = null

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                CommonUtils.showToast(this@OpenCameraActivity, "Permission request granted")
            } else {
                CommonUtils.showToast(this@OpenCameraActivity, "Permission request denied")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpenCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        Permission All Granted Handle
        if (!allPermissionsGranted()) {
//            TODO Request Permission
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }



        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }

            btnCamera.setOnClickListener {
                startCameraX()
            }

            btnGallery.setOnClickListener {
                startGallery()
            }


        }
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

    //    Start Intent Gallery END 👋

    //    Show Image From Camera X ⭐

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            showImage()
        }
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }

}