package com.rhezarijaya.githubone.ui.activity.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rhezarijaya.githubone.data.UserRepository
import com.rhezarijaya.githubone.data.network.response.UserDetailResponse
import com.rhezarijaya.githubone.data.network.retrofit.APIConfig
import com.rhezarijaya.githubone.data.room.entity.Favorite
import com.rhezarijaya.githubone.utils.SingleEvent
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val apiConfig = APIConfig.getGithubAPIService()

    private val _userFollowers = MutableLiveData<List<UserDetailResponse>?>()
    val userFollowers: LiveData<List<UserDetailResponse>?> = _userFollowers

    private val _userFollowing = MutableLiveData<List<UserDetailResponse>?>()
    val userFollowing: LiveData<List<UserDetailResponse>?> = _userFollowing

    private val _isUserFollowersLoading = MutableLiveData<Boolean>()
    val isUserFollowersLoading = _isUserFollowersLoading

    private val _isUserFollowingLoading = MutableLiveData<Boolean>()
    val isUserFollowingLoading = _isUserFollowingLoading

    private val _userFollowersErrorMessage = MutableLiveData<SingleEvent<String>>()
    val userFollowersErrorMessage = _userFollowersErrorMessage

    private val _userFollowingErrorMessage = MutableLiveData<SingleEvent<String>>()
    val userFollowingErrorMessage = _userFollowingErrorMessage

    fun getUserDetail(username: String) = userRepository.getUserDetail(username)

    fun setFavorite(favorite: Favorite) = viewModelScope.launch {
        userRepository.setFavorite(favorite)
    }

    fun removeFavorite(favorite: Favorite) = viewModelScope.launch {
        userRepository.removeFavorite(favorite)
    }

    fun loadUserFollowers(username: String) {
        _isUserFollowersLoading.value = true

        apiConfig.getUserFollowers(username).enqueue(object : Callback<List<UserDetailResponse>> {
            override fun onResponse(
                call: Call<List<UserDetailResponse>>,
                response: Response<List<UserDetailResponse>>
            ) {
                _isUserFollowersLoading.value = false

                if (response.isSuccessful) {
                    _userFollowers.value = response.body()
                } else {
                    _userFollowersErrorMessage.value =
                        SingleEvent("Fetching followers failed. Please try again (${response.message()})")
                }
            }

            override fun onFailure(call: Call<List<UserDetailResponse>>, t: Throwable) {
                isUserFollowersLoading.value = false
                _userFollowersErrorMessage.value =
                    SingleEvent("Fetching followers failed. Please try again")
                Log.d(TAG, "onFailure: ${t.localizedMessage}")
            }
        })
    }

    fun loadUserFollowing(username: String) {
        _isUserFollowingLoading.value = true

        apiConfig.getUserFollowing(username).enqueue(object : Callback<List<UserDetailResponse>> {
            override fun onResponse(
                call: Call<List<UserDetailResponse>>,
                response: Response<List<UserDetailResponse>>
            ) {
                _isUserFollowingLoading.value = false

                if (response.isSuccessful) {
                    _userFollowing.value = response.body()
                } else {
                    _userFollowingErrorMessage.value =
                        SingleEvent("Fetching followings failed. Please try again (${response.message()})")
                }
            }

            override fun onFailure(call: Call<List<UserDetailResponse>>, t: Throwable) {
                isUserFollowingLoading.value = false
                _userFollowingErrorMessage.value =
                    SingleEvent("Fetching followings failed. Please try again")
                Log.d(TAG, "onFailure: ${t.localizedMessage}")
            }
        })
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}