package com.example.coffeeprotectionandanalysissystem.view.detail

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.coffeeprotectionandanalysissystem.R
import com.example.coffeeprotectionandanalysissystem.databinding.ActivityDetailArtikelBinding
import com.example.coffeeprotectionandanalysissystem.ui.article.ArticleViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.log

class DetailArtikelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailArtikelBinding
    private val articleViewModel: ArticleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding
        binding = ActivityDetailArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the toolbar
        setSupportActionBar(binding.topbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // Get article ID from Intent
        val articleId = intent.getIntExtra("articleId", -1)

        if (articleId != -1) {
            Log.d("DetailArtikelActivity", "Article ID: $articleId")
            articleViewModel.fetchArticleById(articleId)
        }

        // Observe the article details
        articleViewModel.article.observe(this) { article ->
            article?.let {
                binding.rvTitle.text = it.title ?: ""
                binding.rvContent.text = it.content ?: ""
                binding.rvDate.text = formatDate(it.createdAt)
                Glide.with(this)
                    .load(it.imageUrl)
                    .into(binding.rvArticle)

                binding.rvSymptomSummary.text = it.symptomSummary ?: ""

                // Ensure to handle nullable lists properly
                binding.rvSymptoms.text = it.symptoms?.joinToString("\n") ?: ""
                binding.rvPreventions.text = it.preventions?.joinToString("\n") ?: ""

                it.treatments?.let { treatments ->
                    binding.rvTreatmentsChemical.text = treatments.chemical ?: ""
                    binding.rvTreatmentsOrganic.text = treatments.organic ?: ""
                }

                binding.rvPlants.text = it.plants?.joinToString(", ") ?: ""
            }
        }

        // Apply window insets using view binding
        ViewCompat.setOnApplyWindowInsetsListener(binding.topbar) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBarsInsets.left, systemBarsInsets.top, systemBarsInsets.right, 0)
            insets
        }

        setupView()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // Method to format the date string
    private fun formatDate(dateString: String?): String {
        return if (dateString.isNullOrEmpty()) {
            ""
        } else {
            try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                val date = inputFormat.parse(dateString)
                if (date != null) {
                    outputFormat.format(date)
                } else {
                    ""
                }
            } catch (e: Exception) {
                dateString // Return the original string if parsing fails
            }
        }
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
