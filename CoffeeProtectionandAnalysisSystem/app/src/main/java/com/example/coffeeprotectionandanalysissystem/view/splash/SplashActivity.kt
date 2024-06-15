package com.example.coffeeprotectionandanalysissystem.view.splash

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeprotectionandanalysissystem.R
import com.example.coffeeprotectionandanalysissystem.view.main.MainActivity

class SplashActivity : AppCompatActivity() {

    companion object {
        private const val SPLASH_SCREEN_TIMEOUT = 1500
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }, SPLASH_SCREEN_TIMEOUT.toLong())

        setupView()
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