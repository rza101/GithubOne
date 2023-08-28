package com.rhezarijaya.githubone.ui.activity.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rhezarijaya.githubone.data.network.response.SearchResponse
import com.rhezarijaya.githubone.data.network.retrofit.APIConfig
import com.rhezarijaya.githubone.utils.SingleEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _searchResult = MutableLiveData<SearchResponse?>()
    val searchResult: LiveData<SearchResponse?> = _searchResult

    private val _errorMessage = MutableLiveData<SingleEvent<String>>()
    val errorMessage: LiveData<SingleEvent<String>> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchUser(username: String) {
        _isLoading.value = true
        _searchResult.value = null

        APIConfig
            .getGithubAPIService()
            .getUsersByUsername(username)
            .enqueue(object : Callback<SearchResponse> {
                override fun onResponse(
                    call: Call<SearchResponse>,
                    response: Response<SearchResponse>
                ) {
                    _isLoading.value = false

                    if (response.isSuccessful) {
                        _searchResult.value = response.body()
                    } else {
                        _errorMessage.value =
                            SingleEvent("User search failed. Please try again (${response.message()})")
                    }
                }

                override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                    _isLoading.value = false
                    _errorMessage.value =
                        SingleEvent("User search failed. Please try again")
                    Log.d(TAG, "onFailure: ${t.localizedMessage}")
                }
            })
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}