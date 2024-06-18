package com.dicoding.trashup.data.network.retrofit

import com.dicoding.trashup.data.network.response.driver.DriverChangePasswordRequest
import com.dicoding.trashup.data.network.response.driver.DriverChangePasswordResponse
import com.dicoding.trashup.data.network.response.driver.DriverEditRequest
import com.dicoding.trashup.data.network.response.driver.DriverEditResponse
import com.dicoding.trashup.data.network.response.driver.DriverResponse
import com.dicoding.trashup.data.network.response.user.UserChangePasswordRequest
import com.dicoding.trashup.data.network.response.user.UserChangePasswordResponse
import com.dicoding.trashup.data.network.response.driver.PickUpUserResponse
import com.dicoding.trashup.data.network.response.user.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface ApiServiceDriver {
    @GET("whoami")
    fun getDriverData(): Call<DriverResponse>

    @GET("activities/ready")
    fun getPickUpUser(): Call<PickUpUserResponse>

    @PUT("whoami/update/driver")
    suspend fun updateDriverProfile(
        @Body request: DriverEditRequest
    ) : DriverEditResponse

    @PUT("whoami/reset/driver")
    suspend fun updateDriverPassword(
        @Body request: DriverChangePasswordRequest
    ) : DriverChangePasswordResponse
}