package com.dicoding.trashup.data.network.response.driver

import com.google.gson.annotations.SerializedName

data class DriverChangePasswordResponse(
    @field:SerializedName("message")
    val message: String,
)

data class DriverChangePasswordRequest(
    @field:SerializedName("password")
    val password: String
)