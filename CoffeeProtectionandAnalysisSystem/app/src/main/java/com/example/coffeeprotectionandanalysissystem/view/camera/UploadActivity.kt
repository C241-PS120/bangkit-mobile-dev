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
            // Implementasikan logika untuk mengupload file di sini.
            showToast("File path: ${file.absolutePath}")
        } ?: run {
            showToast("No image to upload")
        }
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
