package com.rhezarijaya.githubone.ui.activity.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.rhezarijaya.githubone.databinding.ActivitySplashBinding
import com.rhezarijaya.githubone.ui.activity.main.MainActivity
import com.rhezarijaya.githubone.utils.Constants
import com.rhezarijaya.githubone.utils.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val splashViewModel =
            ViewModelProvider(
                this,
                ViewModelFactory.getInstance(this)
            )[SplashViewModel::class.java]

        splashViewModel.getDarkModeEnabled().observe(this) { isEnabled ->
            AppCompatDelegate.setDefaultNightMode(
                if (isEnabled) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
            )
        }

        lifecycleScope.launch(Dispatchers.Default) {
            delay(Constants.SPLASH_SCREEN_DELAY)
            withContext(Dispatchers.Main) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}