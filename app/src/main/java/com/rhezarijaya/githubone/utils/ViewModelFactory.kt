package com.rhezarijaya.githubone.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rhezarijaya.githubone.data.UserRepository
import com.rhezarijaya.githubone.data.datastore.SettingPreferences
import com.rhezarijaya.githubone.di.Injection
import com.rhezarijaya.githubone.ui.activity.detail.DetailViewModel
import com.rhezarijaya.githubone.ui.activity.favorite.FavoriteViewModel
import com.rhezarijaya.githubone.ui.activity.settings.SettingsViewModel
import com.rhezarijaya.githubone.ui.activity.splash.SplashViewModel

class ViewModelFactory private constructor(
    private val userRepository: UserRepository,
    private val settingPreferences: SettingPreferences
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(settingPreferences) as T
        } else if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(settingPreferences) as T
        }

        throw (IllegalArgumentException("Invalid view model class: ${modelClass.name}"))
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (instance == null) {
                synchronized(this) {
                    instance = ViewModelFactory(
                        Injection.provideUserRepository(context),
                        Injection.provideSettingsPreferences(context)
                    )
                }
            }

            return instance!!
        }
    }
}