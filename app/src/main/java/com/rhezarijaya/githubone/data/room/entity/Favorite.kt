package com.rhezarijaya.githubone.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey(autoGenerate = false)
    val username: String,

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String?,
)
