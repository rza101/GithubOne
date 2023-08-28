package com.rhezarijaya.githubone.ui.activity.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rhezarijaya.githubone.data.datastore.SettingPreferences

class SplashViewModel(private val settingPreferences: SettingPreferences) : ViewModel() {
    fun getDarkModeEnabled() = settingPreferences.getDarkModeEnabled().asLiveData()
}