package com.dicoding.trashup.data

import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
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

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance( userPreferences: UserPreferences) : UserRepository =
            instance ?: synchronized(this) {
                instance ?:UserRepository(userPreferences)
            }.also { instance = it }
    }
}