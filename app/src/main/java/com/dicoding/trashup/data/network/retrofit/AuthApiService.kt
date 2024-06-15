package com.dicoding.trashup.data.network.retrofit

import com.dicoding.trashup.data.network.response.DataRegisterDriver
import com.dicoding.trashup.data.network.response.DataRegisterUser
import com.dicoding.trashup.data.network.response.LoginDriverResponse
import com.dicoding.trashup.data.network.response.LoginUserResponse
import com.dicoding.trashup.data.network.response.RegisterDriverResponse
import com.dicoding.trashup.data.network.response.RegisterUserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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