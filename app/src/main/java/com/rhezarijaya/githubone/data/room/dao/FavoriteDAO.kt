package com.rhezarijaya.githubone.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rhezarijaya.githubone.data.room.entity.Favorite

@Dao
interface FavoriteDAO {
    @Query("SELECT * FROM favorite WHERE username = :username")
    fun getFavoriteByUsername(username: String): LiveData<Favorite?>

    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): LiveData<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(favorite: Favorite)

    @Delete
    suspend fun deleteFavorite(favorite: Favorite)
}