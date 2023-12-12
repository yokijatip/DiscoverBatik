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
import androidx.camera.core.ImageCapture
import androidx.core.content.ContextCompat
import com.enigma.discoverbatik.databinding.ActivityCameraBinding
import com.enigma.discoverbatik.utils.CommonUtils
import java.util.concurrent.ExecutorService


class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding

    //    Camera X
    private var imageCapture: ImageCapture? = null
    private var currentImageUri: Uri? = null
    private lateinit var cameraExecutor: ExecutorService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        Conditional Permission
        if (!allPermissionGranted()) {
            // TODO setting start camera
        }

        binding.apply {
            openGallery.setOnClickListener {
                startGallery()
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    //    All Permission State
    private fun allPermissionGranted() = ContextCompat.checkSelfPermission(
        this,
        REQUIRED_PERMISSION
    ) == PackageManager.PERMISSION_GRANTED

//    Start Intent Gallery START ⭐

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
            if (uri != null) {
                currentImageUri = uri
                val intent = Intent(this@CameraActivity, OpenCameraActivity::class.java)
                intent.putExtra("IMAGE_URI", currentImageUri.toString())
                startActivity(intent)
            } else {
                Log.d("Image Uri", "Photo Picker : No Media Selected")
            }
        }

//    private fun showImage() {
//        currentImageUri?.let {
//            Log.d("Image Uri", "Show Uri : $it")
//        }
//    }

    //    Start Intent Gallery END 👋


    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}