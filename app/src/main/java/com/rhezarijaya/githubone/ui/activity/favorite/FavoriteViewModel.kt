package com.rhezarijaya.githubone.ui.activity.favorite

import androidx.lifecycle.ViewModel
import com.rhezarijaya.githubone.data.UserRepository

class FavoriteViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getFavoriteUsers() = userRepository.getFavoriteUsers()
}