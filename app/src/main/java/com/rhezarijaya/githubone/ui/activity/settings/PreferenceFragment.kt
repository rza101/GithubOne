package com.rhezarijaya.githubone.ui.activity.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.rhezarijaya.githubone.R
import com.rhezarijaya.githubone.utils.ViewModelFactory

class PreferenceFragment : PreferenceFragmentCompat() {
    private lateinit var switchPreferenceDarkMode: SwitchPreference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)

        switchPreferenceDarkMode = findPreference(getString(R.string.dark_mode))!!

        val settingsViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getInstance(requireActivity())
        )[SettingsViewModel::class.java]

        settingsViewModel.getDarkModeEnabled().observe(requireActivity()) { isEnabled ->
            switchPreferenceDarkMode.isChecked = isEnabled
            AppCompatDelegate.setDefaultNightMode(
                if (isEnabled) {
                    AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
            )
        }

        switchPreferenceDarkMode.setOnPreferenceChangeListener { _, newValue ->
            settingsViewModel.setDarkModeEnabled(newValue as Boolean)
            true
        }
    }
}