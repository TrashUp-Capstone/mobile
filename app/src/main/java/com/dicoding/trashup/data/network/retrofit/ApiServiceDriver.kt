package com.dicoding.trashup.data.network.retrofit

import com.dicoding.trashup.data.network.response.driver.DriverResponse
import com.dicoding.trashup.data.network.response.driver.PickUpUserResponse
import com.dicoding.trashup.data.network.response.user.UserResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiServiceDriver {
    @GET("whoami")
    fun getDriverData(): Call<DriverResponse>

    @GET("activities/ready")
    fun getPickUpUser(): Call<PickUpUserResponse>
}