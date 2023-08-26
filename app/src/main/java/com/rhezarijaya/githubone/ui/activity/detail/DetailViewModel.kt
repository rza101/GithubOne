package com.rhezarijaya.githubone.ui.activity.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rhezarijaya.githubone.data.network.response.UserDetailResponse
import com.rhezarijaya.githubone.data.network.retrofit.APIConfig
import com.rhezarijaya.githubone.utils.SingleEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    companion object {
        private const val TAG = "DetailViewModel"
    }

    private val apiConfig = APIConfig.getGithubAPIService()

    private val _userDetail = MutableLiveData<UserDetailResponse?>()
    val userDetail: LiveData<UserDetailResponse?> = _userDetail

    private val _userFollowers = MutableLiveData<List<UserDetailResponse>?>()
    val userFollowers: LiveData<List<UserDetailResponse>?> = _userFollowers

    private val _userFollowing = MutableLiveData<List<UserDetailResponse>?>()
    val userFollowing: LiveData<List<UserDetailResponse>?> = _userFollowing

    private val _isUserDetailLoading = MutableLiveData<Boolean>()
    val isUserDetailLoading = _isUserDetailLoading

    private val _isUserFollowersLoading = MutableLiveData<Boolean>()
    val isUserFollowersLoading = _isUserFollowersLoading

    private val _isUserFollowingLoading = MutableLiveData<Boolean>()
    val isUserFollowingLoading = _isUserFollowingLoading

    private val _userDetailErrorMessage = MutableLiveData<SingleEvent<String>>()
    val userDetailErrorMessage = _userDetailErrorMessage

    private val _userFollowersErrorMessage = MutableLiveData<SingleEvent<String>>()
    val userFollowersErrorMessage = _userFollowersErrorMessage

    private val _userFollowingErrorMessage = MutableLiveData<SingleEvent<String>>()
    val userFollowingErrorMessage = _userFollowingErrorMessage

    fun loadUserDetail(username: String) {
        _isUserDetailLoading.value = true

        apiConfig.getUserDetail(username).enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                _isUserDetailLoading.value = false

                if (response.isSuccessful) {
                    _userDetail.value = response.body()
                } else {
                    _userDetailErrorMessage.value =
                        SingleEvent("Fetching user detail failed. Please try again (${response.message()})")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                isUserDetailLoading.value = false
                _userDetailErrorMessage.value =
                    SingleEvent("Fetching user detail failed. Please try again")
                Log.d(TAG, "onFailure: ${t.localizedMessage}")
            }
        })
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
}