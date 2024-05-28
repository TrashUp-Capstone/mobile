package com.dicoding.trashup.di

import android.content.Context
import com.dicoding.trashup.data.UserPreferences
import com.dicoding.trashup.data.UserRepository
import com.dicoding.trashup.data.dataStore

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreferences.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}