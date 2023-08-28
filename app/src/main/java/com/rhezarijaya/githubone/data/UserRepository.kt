package com.rhezarijaya.githubone.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.rhezarijaya.githubone.data.network.response.UserDetailResponse
import com.rhezarijaya.githubone.data.network.retrofit.GithubAPIService
import com.rhezarijaya.githubone.data.room.dao.FavoriteDAO
import com.rhezarijaya.githubone.data.room.entity.Favorite
import com.rhezarijaya.githubone.utils.Result
import com.rhezarijaya.githubone.utils.SingleEvent

class UserRepository(
    private val favoriteDAO: FavoriteDAO,
    private val githubAPIService: GithubAPIService,
) {
    fun getUserDetail(username: String): LiveData<Result<UserDetailResponse>> = liveData {
        emit(Result.Loading)

        try {
            val userDetailResponse = githubAPIService.getUserDetail(username)
            val isFavorite: LiveData<Result<UserDetailResponse>> =
                favoriteDAO.getFavoriteByUsername(username).map {
                    // karena pada DAO berupa nullable, jika tidak ada data akan mengembalikan null
                    userDetailResponse.isOnFavorite = it != null
                    return@map Result.Success(userDetailResponse)
                }

            emitSource(isFavorite)
        } catch (e: Exception) {
            Log.d(TAG, "getUserDetail: ${e.message}")
            emit(Result.Error(SingleEvent("Fetching user detail failed. Please try again")))
        }
    }

    fun getFavoriteUsers(): LiveData<List<UserDetailResponse>> =
        favoriteDAO.getAllFavorites().map { favoriteList ->
            favoriteList.map { favorite ->
                UserDetailResponse(
                    login = favorite.username,
                    id = favorite.userId,
                    avatarUrl = favorite.avatarUrl,
                )
            }
        }

    suspend fun setFavorite(favorite: Favorite) {
        favoriteDAO.insertFavorite(favorite)
    }

    suspend fun removeFavorite(favorite: Favorite) {
        favoriteDAO.deleteFavorite(favorite)
    }

    companion object {
        private const val TAG = "UserRepository"
    }
}