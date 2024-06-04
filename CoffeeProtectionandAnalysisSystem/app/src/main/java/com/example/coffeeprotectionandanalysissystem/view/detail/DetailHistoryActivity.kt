package com.example.coffeeprotectionandanalysissystem.view.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.coffeeprotectionandanalysissystem.databinding.ActivityDetailHistoryBinding

class DetailHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
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
    }
}
