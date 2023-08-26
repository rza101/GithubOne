package com.rhezarijaya.githubone.data.network.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIConfig {
    private const val GITHUB_API_BASE_URL = "https://api.github.com"
    private const val GITHUB_PAT =
        "token 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"

    fun getGithubAPIService(): GithubAPIService {
        val authInterceptor = Interceptor { chain ->
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader("Authorization", GITHUB_PAT)
                    .build()
            )
        }
        val httpLoggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(GITHUB_API_BASE_URL)
            .client(okHttpClient)
            .build()

        return retrofit.create(GithubAPIService::class.java)
    }
}