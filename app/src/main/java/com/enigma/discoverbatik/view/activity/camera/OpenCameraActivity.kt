package com.enigma.discoverbatik.view.activity.camera

import android.Manifest
import android.annotation.SuppressLint
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
import com.enigma.discoverbatik.data.remote.response.PredictionResponse
import com.enigma.discoverbatik.data.remote.service.ApiService
import com.enigma.discoverbatik.databinding.ActivityOpenCameraBinding
import com.enigma.discoverbatik.utils.CommonUtils
import com.enigma.discoverbatik.view.activity.camera.CameraActivity.Companion.CAMERAX_RESULT
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.ByteArrayOutputStream

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
//                val intent = Intent(345
//                    Intent.ACTION_VIEW,
//                    Uri.parse("https://www.google.com/search?q=${tvLabel.text}")
//                )
//                startActivity(intent)
                convertImageToBitmap()
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
//            val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(it))
//            imageView.setImageBitmap(bitmap)
            sendPredictionRequest(it)
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

//    private fun sendPredictionRequest(bitmap: Bitmap) {
//        val byteArrayOutputStream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
//        val byteArray = byteArrayOutputStream.toByteArray()
//
//        val requestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(), byteArray)
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://ml-api-xwe5h6l4uq-et.a.run.app/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val predictionService = retrofit.create(ApiService::class.java)
//
//        val call = predictionService.uploadImage(requestBody)
//
//        call.enqueue(object : Callback<PredictionResponse> {
//            override fun onResponse(
//                call: Call<PredictionResponse>,
//                response: Response<PredictionResponse>
//            ) {
//                if (response.isSuccessful) {
//                    val predictionData = response.body()?.data
//                    val message = response.body()?.status?.message
//
//                    // Handle the prediction result
//                    if (predictionData != null && message != null) {
//                        binding.tvLabel.text = predictionData.batik_prediction
//                        binding.tvLabelProbability.text = predictionData.confidence.toString()
//                    } else {
//                        CommonUtils.showToast(
//                            this@OpenCameraActivity,
//                            "Failed to parse response body"
//                        )
//                    }
//                } else {
//                    CommonUtils.showToast(
//                        this@OpenCameraActivity,
//                        "Unsuccesful response : ${response.code()}"
//                    )
//                }
//            }
//
//            override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
//                CommonUtils.showToast(this@OpenCameraActivity, "Request Failed : ${t.message}")
//            }
//        })
//    }

//    Berdasarkan Byte Array

    @SuppressLint("Recycle")
    private fun sendPredictionRequest(imageUri: Uri) {
        val inputStream = contentResolver.openInputStream(imageUri)
        val bytes = inputStream?.readBytes()

        if (bytes != null) {
            val requestBody = RequestBody.create("image/*".toMediaTypeOrNull(), bytes)
            val imagePart = MultipartBody.Part.createFormData("image", "image.jpg", requestBody)

            val retrofit = Retrofit.Builder()
                .baseUrl("https://ml-api-xwe5h6l4uq-et.a.run.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val predictionService = retrofit.create(ApiService::class.java)

            val call = predictionService.uploadImage(imagePart)

            call.enqueue(object : Callback<PredictionResponse> {
                override fun onResponse(call: Call<PredictionResponse>, response: Response<PredictionResponse>) {
                    if (response.isSuccessful) {
                        val predictionData = response.body()?.data

                        // Handle the prediction result
                        if (predictionData != null) {
                            val message = "Prediction: ${predictionData.batik_prediction}\nConfidence: ${predictionData.confidence}"
                            showToast(message)
                        } else {
                            showToast("Failed to parse response body")
                        }
                    } else {
                        showToast("Unsuccessful response: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                    showToast("Request failed: ${t.message}")
                }
            })
        }
    }

    private fun showToast(message: String) {
        runOnUiThread {
            CommonUtils.showToast(this, message)
        }
    }


}