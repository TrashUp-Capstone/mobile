package com.dicoding.trashup.data.network.retrofit

import com.dicoding.trashup.data.network.response.register.LoginDriverResponse
import com.dicoding.trashup.data.network.response.register.LoginUserResponse
import com.dicoding.trashup.data.network.response.register.RegisterDriverResponse
import com.dicoding.trashup.data.network.response.register.RegisterUserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("login/driver")
    fun loginDriver(@Body requestBody: Map<String, String>): Call<LoginDriverResponse>

    @POST("login/user")
    fun loginUser(@Body requestBody: Map<String, String>): Call<LoginUserResponse>

    @POST("register/user")
    fun registerUser(
        @Body requestBody: Map<String, String>) : Call<RegisterUserResponse>

    @POST("register/driver")
    fun registerDriver(
        @Body requestBody: Map<String, String>) : Call<RegisterDriverResponse>

}