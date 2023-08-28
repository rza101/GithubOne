package com.rhezarijaya.githubone.di

import android.content.Context
import com.rhezarijaya.githubone.data.UserRepository
import com.rhezarijaya.githubone.data.datastore.SettingPreferences
import com.rhezarijaya.githubone.data.datastore.dataStore
import com.rhezarijaya.githubone.data.network.retrofit.APIConfig
import com.rhezarijaya.githubone.data.room.GithubDatabase

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val favoriteDAO = GithubDatabase.getInstance(context).favoriteDAO()
        val githubAPIService = APIConfig.getGithubAPIService()

        return UserRepository(favoriteDAO, githubAPIService)
    }

    fun provideSettingsPreferences(context: Context): SettingPreferences {
        return SettingPreferences.getInstance(context.dataStore)
    }
}