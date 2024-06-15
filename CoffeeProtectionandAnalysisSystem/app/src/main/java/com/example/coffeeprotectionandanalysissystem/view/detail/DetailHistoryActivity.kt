package com.example.coffeeprotectionandanalysissystem.view.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.coffeeprotectionandanalysissystem.R
import com.example.coffeeprotectionandanalysissystem.databinding.ActivityDetailHistoryBinding

class DetailHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.topbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

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

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
