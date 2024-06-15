package com.dicoding.trashup.di

import android.content.Context
import com.dicoding.trashup.data.UserPreferences
import com.dicoding.trashup.data.UserRepository
import com.dicoding.trashup.data.dataStore
import com.dicoding.trashup.data.network.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val userApiService = ApiConfig.getUserApiService(user.token.toString())
        return UserRepository.getInstance(userApiService, pref)
    }
}