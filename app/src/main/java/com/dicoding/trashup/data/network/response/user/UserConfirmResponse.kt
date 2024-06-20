package com.dicoding.trashup.data.network.response.user

import com.google.gson.annotations.SerializedName

data class UserConfirmResponse(
    @field:SerializedName("message")
    val message: String,
)

data class UserConfirmRequest(
    @field:SerializedName("id")
    val id: String,
)
