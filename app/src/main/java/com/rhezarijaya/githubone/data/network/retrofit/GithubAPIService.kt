package com.rhezarijaya.githubone.data.network.retrofit

import com.rhezarijaya.githubone.data.network.response.SearchResponse
import com.rhezarijaya.githubone.data.network.response.UserDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubAPIService {
    @GET("search/users")
    fun getUsersByUsername(@Query("q") username: String): Call<SearchResponse>

    @GET("users/{username}")
    fun getUserDetail(@Path("username") username: String): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    fun getUserFollowers(@Path("username") username: String): Call<List<UserDetailResponse>>

    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") username: String): Call<List<UserDetailResponse>>
}