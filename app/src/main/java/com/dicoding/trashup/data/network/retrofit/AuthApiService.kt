package com.dicoding.trashup.data.network.retrofit

import com.dicoding.trashup.data.network.response.DataRegisterDriver
import com.dicoding.trashup.data.network.response.DataRegisterUser
import com.dicoding.trashup.data.network.response.LoginDriverResponse
import com.dicoding.trashup.data.network.response.LoginUserResponse
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


    @FormUrlEncoded
    @POST("register/user")
    fun registerUser(
        @Field("name") name:String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("birth") birth: String,
        @Field("phone") phone: String,
        @Field("address") address: String
    ) : Call<List<DataRegisterUser>>

    @FormUrlEncoded
    @POST("register/driver")
    fun registerDriver(
        @Field("name") name:String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("address") address: String,
        @Field("birth") birth: String,
        @Field("phone") phone: String,
        @Field("license_plate") licensePlate: String
    ) : Call<List<DataRegisterDriver>>

}