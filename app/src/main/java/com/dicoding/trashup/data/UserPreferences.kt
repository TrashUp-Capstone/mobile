package com.dicoding.trashup.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore:DataStore<Preferences> by preferencesDataStore(name = "session")
class UserPreferences private constructor(private val dataStore: DataStore<Preferences>){
    suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = user.token as String
            preferences[ISUSER_KEY] = user.isUser as Boolean
            preferences[ISDRIVER_KEY] = user.isDriver as Boolean
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel (
                preferences[TOKEN_KEY],
                preferences[ISUSER_KEY],
                preferences[ISDRIVER_KEY]
            )
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE:UserPreferences? = null

        private val TOKEN_KEY = stringPreferencesKey("token")
        private val ISUSER_KEY = booleanPreferencesKey("isuser")
        private val ISDRIVER_KEY = booleanPreferencesKey("isdriver")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance =UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}