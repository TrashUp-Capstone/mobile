package com.dicoding.trashup.data

import com.dicoding.trashup.data.network.response.user.UserData
import com.dicoding.trashup.data.network.response.user.UserResponse
import com.dicoding.trashup.data.network.retrofit.ApiService
import com.dicoding.trashup.data.network.retrofit.user.UserApiService
import kotlinx.coroutines.flow.Flow

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
        return userApiService.getUserData()
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