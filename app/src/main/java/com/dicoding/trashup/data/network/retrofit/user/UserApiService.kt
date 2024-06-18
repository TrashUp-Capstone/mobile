package com.dicoding.trashup.data.network.retrofit.user

import com.dicoding.trashup.data.network.response.driver.PickUpUserResponse
import com.dicoding.trashup.data.network.response.user.AddWasteResponse
import com.dicoding.trashup.data.network.response.user.RedeemVoucherRequest
import com.dicoding.trashup.data.network.response.user.SendWasteResponse
import com.dicoding.trashup.data.network.response.user.SubmitPhotoResponse
import com.dicoding.trashup.data.network.response.user.SubmitWasteResponse
import com.dicoding.trashup.data.network.response.user.UserActivityResponse
import com.dicoding.trashup.data.network.response.user.UserData
import com.dicoding.trashup.data.network.response.user.UserHistoryVoucherResponse
import com.dicoding.trashup.data.network.response.user.UserRedeemedVoucherResponse
import com.dicoding.trashup.data.network.response.user.UserResponse
import com.dicoding.trashup.data.network.response.user.UserVoucherResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part

interface UserApiService {
    @GET("whoami")
    suspend fun getUserData() : UserResponse

    @GET("activities/user")
    suspend fun getUserActivities() : UserActivityResponse

    @GET("lookups/voucher")
    suspend fun getVoucherList() : UserVoucherResponse

    @GET("vouchers")
    suspend fun getUserVoucherList() : UserHistoryVoucherResponse

    @POST("activities/vouchers/new")
    suspend fun redeemVoucher(
        @Body request: RedeemVoucherRequest
    ) : UserRedeemedVoucherResponse

    @POST("activities/new")
    fun createActivity(@Body requestBody: Map<String, Double>): Call<AddWasteResponse>

    @POST("activities/details")
    fun submitListWaste(@Body requestBody: Map<String, String>): Call<SubmitWasteResponse>

    @Multipart
    @POST("details/upload")
    fun addPhoto(
        @Part photo: MultipartBody.Part
    ): Call<SubmitPhotoResponse>

    @PUT("activities/update/ready")
    fun sendWaste(@Body requestBody: Map<String, String>) : Call<SendWasteResponse>


}