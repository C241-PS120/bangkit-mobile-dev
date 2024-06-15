package com.example.coffeeprotectionandanalysissystem.view.result

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.coffeeprotectionandanalysissystem.database.AppDatabase
import com.example.coffeeprotectionandanalysissystem.database.History
import com.example.coffeeprotectionandanalysissystem.databinding.ActivityResultBinding
import com.example.coffeeprotectionandanalysissystem.view.main.MainActivity
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUrl = intent.getStringExtra("imageUrl")
        val label = intent.getStringExtra("label")
        val suggestion = intent.getStringExtra("suggestion")

        imageUrl?.let {
            Glide.with(this)
                .load(it)
                .into(binding.ivLeaf)
        }
        binding.label.text = label
        binding.suggestion.text = suggestion
        binding.saveButton.setOnClickListener {
            val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
            saveToDatabase(imageUrl, label, suggestion, currentDate)
        }
        binding.artikelButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("label", label)
            intent.putExtra("navigateToArticle", true)
            startActivity(intent)
            finish()
        }

    }

    private fun saveToDatabase(imageUrl: String?, label: String?, suggestion: String?, date: String?) {
        if (imageUrl != null && label != null && suggestion != null && date != null) {
            val history = History(
                imageId = imageUrl,
                label = label,
                suggestion = suggestion,
                date = date
            )
            lifecycleScope.launch {
                AppDatabase.getDatabase(this@ResultActivity).historyDao().addHistory(history)
                runOnUiThread {
                    Toast.makeText(this@ResultActivity, "Riwayat Berhasil Disimpan!", Toast.LENGTH_SHORT).show()
                    navigateToMainActivity()
                }
            }
        } else {
            Toast.makeText(this, "Cannot save. Data is missing.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
