package com.example.coffeeprotectionandanalysissystem.view.camera

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.coffeeprotectionandanalysissystem.databinding.ActivityUploadBinding
import com.example.coffeeprotectionandanalysissystem.response.PredictionResponse
import com.example.coffeeprotectionandanalysissystem.service.ApiConfig
import com.example.coffeeprotectionandanalysissystem.view.result.ResultActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Locale

class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding
    private var photoUri: Uri? = null
    private val timeStamp: String = SimpleDateFormat(
        DATE_FORMAT,
        Locale.US
    ).format(System.currentTimeMillis())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemUI()

        photoUri = Uri.parse(intent.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE))

        Glide.with(this)
            .load(photoUri)
            .into(binding.ivStory)

        binding.btnClose.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.uploadButton.setOnClickListener { uploadImage() }
    }

    private fun uploadImage() {
        photoUri?.let {
            val file = uriToFile(it, this)
            val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), file)
            val body = MultipartBody.Part.createFormData("photo", file.name, requestFile)

            showLoading(true)
            ApiConfig.apiService.getPrediction(body).enqueue(object : Callback<PredictionResponse> {
                override fun onResponse(call: Call<PredictionResponse>, response: Response<PredictionResponse>) {
                    showLoading(false)
                    if (response.isSuccessful) {
                        response.body()?.let { predictionResponse ->
                            if (predictionResponse.status == "error") {
                                showToast(predictionResponse.message ?: "Failed to recognize image")
                            } else {
                                val intent = Intent(this@UploadActivity, ResultActivity::class.java).apply {
                                    putExtra("imageUrl", predictionResponse.data?.imageUrl)
                                    putExtra("label", predictionResponse.data?.label)
                                    putExtra("id", predictionResponse.data?.id)
                                    putExtra("suggestion", predictionResponse.data?.suggestion)
                                }
                                startActivity(intent)
                            }
                        }
                    } else {
                        // Parse the error response body to extract the message
                        val errorResponse = response.errorBody()?.string()
                        val errorMessage = extractErrorMessage(errorResponse)
                        showToast(errorMessage)
                    }
                }

                override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                    showLoading(false)
                    showToast("Error: ${t.message}")
                }
            })
        } ?: run {
            showToast("No image to upload")
        }
    }

    private fun extractErrorMessage(errorBody: String?): String {
        errorBody?.let {
            try {
                val jsonObject = JSONObject(it)
                return jsonObject.getString("message")
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return "Failed to recognize image"
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun uriToFile(imageUri: Uri, context: Context): File {
        val myFile = createCustomTempFile(context)
        val inputStream = context.contentResolver.openInputStream(imageUri) as InputStream
        val outputStream = FileOutputStream(myFile)
        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(buffer, 0, length)
        outputStream.close()
        inputStream.close()
        return myFile
    }

    private fun createCustomTempFile(context: Context): File {
        val filesDir = context.externalCacheDir
        return File.createTempFile(timeStamp, ".jpg", filesDir)
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    companion object {
        private const val DATE_FORMAT = "dd-MMM-yyyy"
    }
}
