package com.dicoding.trashup.data

import android.util.Log
import com.dicoding.trashup.data.network.response.user.RedeemVoucherRequest
import com.dicoding.trashup.data.network.response.user.UserActivityResponse
import com.dicoding.trashup.data.network.response.user.UserData
import com.dicoding.trashup.data.network.response.user.UserEditRequest
import com.dicoding.trashup.data.network.response.user.UserEditResponse
import com.dicoding.trashup.data.network.response.user.UserHistoryVoucherResponse
import com.dicoding.trashup.data.network.response.user.UserRedeemedVoucherResponse
import com.dicoding.trashup.data.network.response.user.UserResponse
import com.dicoding.trashup.data.network.response.user.UserVoucherResponse
import com.dicoding.trashup.data.network.retrofit.ApiConfig
import com.dicoding.trashup.data.network.retrofit.ApiService
import com.dicoding.trashup.data.network.retrofit.user.UserApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class UserRepository private constructor(
    private val userApiService: UserApiService,
    private val userPreferences: UserPreferences
){
    suspend fun saveSession(userModel: UserModel) {
        userPreferences.saveSession(userModel)
    }
    fun getSession(): Flow<UserModel> {
        return  userPreferences.getSession()
    }
    suspend fun logout() {
        userPreferences.logout()
    }

    // User
    suspend fun getUserData(): UserResponse {
        val userModel = userPreferences.getSession().first()
        val token = userModel.token
        val userApiService = ApiConfig.getUserApiService(token.toString())
        return userApiService.getUserData()
    }

    suspend fun getUserActivities(): UserActivityResponse {
        val userModel = getSession().first()
        val token = userModel.token
        val userApiService = ApiConfig.getUserApiService(token.toString())
        return userApiService.getUserActivities()
    }

    suspend fun getVoucherList(): UserVoucherResponse {
        val userModel = getSession().first()
        val token = userModel.token
        val userApiService = ApiConfig.getUserApiService(token.toString())
        return userApiService.getVoucherList()
    }

    suspend fun getUserVoucherList(): UserHistoryVoucherResponse {
        val userModel = getSession().first()
        val token = userModel.token
        val userApiService = ApiConfig.getUserApiService(token.toString())
        return userApiService.getUserVoucherList()
    }

    suspend fun redeemVoucher(voucherTypeId: Int): UserRedeemedVoucherResponse {
        val userModel = getSession().first()
        val token = userModel.token
        val userApiService = ApiConfig.getUserApiService(token.toString())
        val request = RedeemVoucherRequest(voucherTypeId)
        Log.d("COY REPO", "voucherTypeId: $voucherTypeId")
        return userApiService.redeemVoucher(request)
    }

    suspend fun updateUserProfile(
        name: String,
        email: String,
        phone: String,
        birth: String,
        address: String,
        password: String,
    ): UserEditResponse {
        val userModel = getSession().first()
        val token = userModel.token
        val userApiService = ApiConfig.getUserApiService(token.toString())
        val request = UserEditRequest(name, email, phone, birth, address, password)
        Log.d("COY REQ update profile", "request: $request")
        return userApiService.updateUserProfile(request)
    }



    companion object {

        fun getInstance(
            userApiService: UserApiService,
            userPreference: UserPreferences
        ) = UserRepository(userApiService, userPreference)

//        @Volatile
//        private var instance: UserRepository? = null

//        fun getInstance( userPreferences: UserPreferences) : UserRepository =
//            instance ?: synchronized(this) {
//                instance ?:UserRepository(userPreferences)
//            }.also { instance = it }

    }
}