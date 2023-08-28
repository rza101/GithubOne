package com.rhezarijaya.githubone.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rhezarijaya.githubone.data.room.dao.FavoriteDAO
import com.rhezarijaya.githubone.data.room.entity.Favorite

@Database(entities = [Favorite::class], version = 1)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun favoriteDAO(): FavoriteDAO

    companion object {
        private const val DATABASE_NAME = "db_githubone"

        @Volatile
        private var instance: GithubDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): GithubDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GithubDatabase::class.java,
                        DATABASE_NAME
                    ).build()
                }
            }

            return instance!!
        }
    }
}