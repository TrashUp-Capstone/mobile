package com.dicoding.trashup.ui.signin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.trashup.data.UserModel
import com.dicoding.trashup.data.UserRepository
import com.dicoding.trashup.data.network.response.ErrorResponse
import com.dicoding.trashup.data.network.response.register.LoginDriverResponse
import com.dicoding.trashup.data.network.retrofit.ApiConfig
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SigninDriverViewModel (private val repository: UserRepository): ViewModel() {
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _resultAccount = MutableLiveData<String>()
    val resultAccount: LiveData<String> = _resultAccount

    private val _message = MutableLiveData<String>()
    val message : LiveData<String> = _message

    fun loginDriver(email: String, password: String) {
        _isLoading.value = true

        val requestBody = mapOf(
            "email" to email,
            "password" to password
        )

        val client = ApiConfig.getAuthApiService().loginDriver(requestBody)
        Log.e("Email", email)
        Log.e("Password", password)
        client.enqueue(object : Callback<LoginDriverResponse> {
            override fun onResponse(
                call: Call<LoginDriverResponse>,
                response: Response<LoginDriverResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _resultAccount.value = responseBody?.token
                } else {
                    val errorJson = response.errorBody()?.string()
                    val gson = Gson()
                    try {
                        val errorResponse = gson.fromJson(errorJson, ErrorResponse::class.java)
                        _message.value = errorResponse.message
                        Log.e(TAG, "Error Message: ${errorResponse.message}")
                    } catch (e: Exception) {
                        _message.value = "Unknown error"
                        Log.e(TAG, "Parsing error: ${e.message}")
                    }
                }
            }

            override fun onFailure(call: Call<LoginDriverResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure2: ${t.message.toString()}")
            }
        })
    }

    companion object {
        const val  TAG = "SigninDriverViewModel"
    }

}