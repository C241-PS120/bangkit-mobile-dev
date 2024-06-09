package com.example.coffeeprotectionandanalysissystem.view.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.coffeeprotectionandanalysissystem.databinding.ActivityDetailArtikelBinding
import java.text.SimpleDateFormat
import java.util.Locale

class DetailArtikelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailArtikelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize view binding
        binding = ActivityDetailArtikelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data from Intent
        val title = intent.getStringExtra("title")
        val imageUrl = intent.getStringExtra("imageUrl")
        val content = intent.getStringExtra("content")
        val author = intent.getStringExtra("author")
        val createdAt = intent.getStringExtra("createdAt")

        // Set data to views
        binding.rvTitle.text = title
        binding.rvContent.text = content
        binding.rvAuthor.text = author
        binding.rvDate.text = formatDate(createdAt)
        Glide.with(this)
            .load(imageUrl)
            .into(binding.rvArticle)

        // Apply window insets using view binding
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
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
}
