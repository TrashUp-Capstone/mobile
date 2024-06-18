package com.dicoding.trashup.data.network.response.user

import com.google.gson.annotations.SerializedName

data class UserChangePasswordResponse(
    @field:SerializedName("message")
    val message: String,
)

data class UserChangePasswordRequest(
    @field:SerializedName("password")
    val password: String
)