package com.example.coffeeprotectionandanalysissystem.view.result

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.coffeeprotectionandanalysissystem.databinding.ActivityResultBinding

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
    }
}

