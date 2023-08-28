package com.rhezarijaya.githubone.ui.activity.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.rhezarijaya.githubone.R
import com.rhezarijaya.githubone.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.settings)
        }

        supportFragmentManager.commit {
            add(binding.frameSetting.id, PreferenceFragment())
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}