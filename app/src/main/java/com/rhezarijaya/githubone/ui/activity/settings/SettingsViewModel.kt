package com.rhezarijaya.githubone.ui.activity.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rhezarijaya.githubone.data.datastore.SettingPreferences
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingPreferences: SettingPreferences) : ViewModel() {
    fun getDarkModeEnabled() = settingPreferences.getDarkModeEnabled().asLiveData()

    fun setDarkModeEnabled(isDarkModeActive: Boolean) = viewModelScope.launch {
        settingPreferences.setDarkModeEnabled(isDarkModeActive)
    }
}