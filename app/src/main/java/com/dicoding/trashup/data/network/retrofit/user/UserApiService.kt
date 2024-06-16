package com.dicoding.trashup.data.network.retrofit.user

import com.dicoding.trashup.data.network.response.user.UserActivityResponse
import com.dicoding.trashup.data.network.response.user.UserData
import com.dicoding.trashup.data.network.response.user.UserHistoryVoucherResponse
import com.dicoding.trashup.data.network.response.user.UserRedeemedVoucherResponse
import com.dicoding.trashup.data.network.response.user.UserResponse
import com.dicoding.trashup.data.network.response.user.UserVoucherResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApiService {
    @GET("whoami")
    suspend fun getUserData() : UserResponse

    @GET("activities/user")
    suspend fun getUserActivities() : UserActivityResponse

    @GET("lookups/voucher")
    suspend fun getVoucherList() : UserVoucherResponse

    @GET("vouchers")
    suspend fun getUserVoucherList() : UserHistoryVoucherResponse

    @FormUrlEncoded
    @POST("activities/vouchers/new")
    suspend fun redeemVoucher(
        @Field("voucher_type_id") voucherTypeId : String
    ) : UserRedeemedVoucherResponse
}