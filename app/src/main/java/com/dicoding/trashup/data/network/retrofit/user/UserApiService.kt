package com.dicoding.trashup.data.network.retrofit.user

import com.dicoding.trashup.data.network.response.user.UserActivityResponse
import com.dicoding.trashup.data.network.response.user.UserData
import com.dicoding.trashup.data.network.response.user.UserResponse
import retrofit2.Call
import retrofit2.http.GET

interface UserApiService {
    @GET("whoami")
    suspend fun getUserData() : UserResponse

    @GET("activities/user")
    suspend fun getUserActivities() : UserActivityResponse
}