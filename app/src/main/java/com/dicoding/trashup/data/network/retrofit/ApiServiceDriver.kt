package com.dicoding.trashup.data.network.retrofit

import com.dicoding.trashup.data.network.response.driver.CompletedDeliveryResponse
import com.dicoding.trashup.data.network.response.driver.ConfirmPickUpUserResponse
import com.dicoding.trashup.data.network.response.driver.DriverChangePasswordRequest
import com.dicoding.trashup.data.network.response.driver.DriverChangePasswordResponse
import com.dicoding.trashup.data.network.response.driver.DriverEditRequest
import com.dicoding.trashup.data.network.response.driver.DriverEditResponse
import com.dicoding.trashup.data.network.response.driver.DriverResponse
import com.dicoding.trashup.data.network.response.driver.ListWastePickUpResponse
import com.dicoding.trashup.data.network.response.user.UserChangePasswordRequest
import com.dicoding.trashup.data.network.response.user.UserChangePasswordResponse
import com.dicoding.trashup.data.network.response.driver.PickUpUserResponse
import com.dicoding.trashup.data.network.response.driver.UserGoingResponse
import com.dicoding.trashup.data.network.response.user.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("activities/details")
    fun getListWaste(@Query("activity_id") activityId: String) : Call<ListWastePickUpResponse>

    @PUT("activities/update/assign")
    fun  confirmUser(@Body requestBody: Map<String, String>): Call<ConfirmPickUpUserResponse>

    @GET("activities/driver")
    fun onGoingUser(): Call<UserGoingResponse>

    @PUT("activities/update/delivered")
    fun completeDelivery(@Body requestBody: Map<String, String>): Call<CompletedDeliveryResponse>
}