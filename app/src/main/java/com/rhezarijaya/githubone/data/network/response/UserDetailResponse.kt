package com.rhezarijaya.githubone.data.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// user detail memiliki cukup banyak property yang sama pada beberapa endpoint
// sehingga tidak perlu dibuat duplikat untuk semua endpoint
@Parcelize
data class UserDetailResponse(
    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("public_repos")
    val publicRepos: Int? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("followers")
    val followers: Int? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @field:SerializedName("following")
    val following: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    var isOnFavorite: Boolean = false,
) : Parcelable
