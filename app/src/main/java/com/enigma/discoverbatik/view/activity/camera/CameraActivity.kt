package com.enigma.discoverbatik.view.activity.camera

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.enigma.discoverbatik.databinding.ActivityCameraBinding
import com.enigma.discoverbatik.utils.CommonUtils
import com.enigma.discoverbatik.utils.Tools
import java.util.concurrent.ExecutorService


class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding

    //    Camera X
    private var imageCapture: ImageCapture? = null
    private var currentImage: Uri? = null
    private lateinit var cameraExecutor: ExecutorService
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            changeCamera.setOnClickListener {
                cameraSelector =
                    if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) CameraSelector.DEFAULT_FRONT_CAMERA
                    else CameraSelector.DEFAULT_BACK_CAMERA
                startCamera()
                CommonUtils.showToast(this@CameraActivity, "Click")
            }
            imageCapture.setOnClickListener {
                takePhoto()
            }
        }


    }

    public override fun onResume() {
        super.onResume()
        startCamera()
    }

    //    Function Start Camera
    private fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture

                )
            } catch (exc: Exception) {
                CommonUtils.showToast(this@CameraActivity, "Failed Open Camera X")
                Log.e(TAG, "startCamera: ${exc.message}")
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = Tools().createCustomTempFile(application)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    CommonUtils.showToast(this@CameraActivity, "Image Captured")
                    val intent = Intent()
                    intent.putExtra(EXTRA_CAMERAX_IMAGE, outputFileResults.savedUri.toString())
                    setResult(CAMERAX_RESULT, intent)
                    finish()
                }

                override fun onError(exception: ImageCaptureException) {
                    CommonUtils.showToast(this@CameraActivity, "Failed Image Captured")
                    Log.e(TAG, "onError: ${exception.message}")
                }

            }
        )
    }


//    Start Intent Gallery START ⭐

//    private fun startGallery() {
//        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//    }
//
//    private val launcherGallery =
//        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
//            if (uri != null) {
//                currentImageUri = uri
//                val intent = Intent(this@CameraActivity, OpenCameraActivity::class.java)
//                intent.putExtra("IMAGE_URI", currentImageUri.toString())
//                startActivity(intent)
//            } else {
//                Log.d("Image Uri", "Photo Picker : No Media Selected")
//            }
//        }

//    private fun showImage() {
//        currentImageUri?.let {
//            Log.d("Image Uri", "Show Uri : $it")
//        }
//    }

    //    Start Intent Gallery END 👋


    companion object {
        private const val TAG = "CameraActivity"

        //        Keperluan untuk menyimpan photo dan melihat photo dan juga mengirim photo ke activity yang di tuju
        const val EXTRA_CAMERAX_IMAGE = "CameraX Image"
        const val CAMERAX_RESULT = 200
    }
}