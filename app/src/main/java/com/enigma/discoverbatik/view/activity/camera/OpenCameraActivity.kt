package com.enigma.discoverbatik.view.activity.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.enigma.discoverbatik.databinding.ActivityOpenCameraBinding
import com.enigma.discoverbatik.ml.ManukClassifier
import com.enigma.discoverbatik.utils.CommonUtils
import com.enigma.discoverbatik.view.activity.camera.CameraActivity.Companion.CAMERAX_RESULT
import org.tensorflow.lite.support.image.TensorImage

@Suppress("DEPRECATION")
class OpenCameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOpenCameraBinding
    private lateinit var imageView: ImageView
    private lateinit var tvLabel: TextView
    private lateinit var tvLabelProbability: TextView
    private lateinit var btnFind: Button

    private var currentImageUri: Uri? = null

    private fun allPermissionsGranted() = ContextCompat.checkSelfPermission(
        this,
        REQUIRED_PERMISSION
    ) == PackageManager.PERMISSION_GRANTED

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
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

//        Deklarasi component xml
        imageView = binding.contentImage
        btnFind = binding.btnFind
        tvLabel = binding.tvLabel
        tvLabelProbability = binding.tvLabelProbability




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

            btnFind.setOnClickListener {
//                val intent = Intent(
//                    Intent.ACTION_VIEW,
//                    Uri.parse("https://www.google.com/search?q=${tvLabel.text}")
//                )
//                startActivity(intent)
                CommonUtils.showToast(this@OpenCameraActivity, "Feature will be implemented")
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
                //  showImage()
                convertImageToBitmap()
            } else {
                Log.d("Image Uri", "Photo Picker : No Media Selected")
            }
        }

    //    Start Intent Gallery END 👋

    private fun convertImageToBitmap() {
        currentImageUri?.let {
            val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(it))
            imageView.setImageBitmap(bitmap)
        }
    }


    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            convertImageToBitmap()
        }
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }

}