package com.example.coffeeprotectionandanalysissystem.view.result

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.coffeeprotectionandanalysissystem.adapter.SymptomsAdapter
import com.example.coffeeprotectionandanalysissystem.database.AppDatabase
import com.example.coffeeprotectionandanalysissystem.database.History
import com.example.coffeeprotectionandanalysissystem.databinding.ActivityResultBinding
import com.example.coffeeprotectionandanalysissystem.view.main.MainActivity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.bumptech.glide.request.target.Target

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var symptomsAdapter: SymptomsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUrl = intent.getStringExtra("imageUrl")
        val label = intent.getStringExtra("label")
        val suggestion = intent.getStringExtra("suggestion")
        val symptoms = intent.getStringArrayListExtra("symptoms")

        imageUrl?.let {
            Glide.with(this)
                .load(it)
                .override(Target.SIZE_ORIGINAL) // This will make the image view the same size as the original image
                .into(binding.ivLeaf)
        }
        binding.label.text = label
        binding.suggestion.text = suggestion
        setupRecyclerView()
        loadSymptoms(symptoms)
        binding.saveButton.setOnClickListener {
            val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
            saveToDatabase(imageUrl, label, suggestion, currentDate, symptoms)
        }
        ViewCompat.setOnApplyWindowInsetsListener(binding.topbar) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBarsInsets.left, systemBarsInsets.top, systemBarsInsets.right, 0)
            insets
        }

        setupView()
    }

    private fun setupRecyclerView() {
        symptomsAdapter = SymptomsAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ResultActivity)
            adapter = symptomsAdapter
        }
    }

    private fun loadSymptoms(symptoms: ArrayList<String>?) {
        symptoms?.let {
            symptomsAdapter.setSymptoms(it)
        }
    }

    private fun saveToDatabase(
        imageUrl: String?,
        label: String?,
        suggestion: String?,
        date: String?,
        symptoms: ArrayList<String>?
    ) {
        if (imageUrl != null && label != null && suggestion != null && date != null && symptoms != null) {
            val symptomsList: List<String> = symptoms.toList()

            val history = History(
                imageId = imageUrl,
                label = label,
                suggestion = suggestion,
                date = date,
                symptoms = symptomsList
            )

            lifecycleScope.launch {
                AppDatabase.getDatabase(this@ResultActivity).historyDao().addHistory(history)
                runOnUiThread {
                    Toast.makeText(
                        this@ResultActivity,
                        "Riwayat Berhasil Disimpan!",
                        Toast.LENGTH_SHORT
                    ).show()
                    navigateToMainActivity()
                }
            }
        } else {
            Toast.makeText(this, "Cannot save. Data is missing.", Toast.LENGTH_SHORT).show()
        }
    }


    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setupView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
            window.statusBarColor = Color.TRANSPARENT
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    )
            window.statusBarColor = Color.TRANSPARENT
        }
    }
}
