package com.dicoding.trashup.ui.register.registeruser

import android.provider.ContactsContract.CommonDataKinds.Phone
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.trashup.data.network.response.DataRegisterUser
import com.dicoding.trashup.data.network.response.ErrorResponse
import com.dicoding.trashup.data.network.response.RegisterUserResponse
import com.dicoding.trashup.data.network.retrofit.ApiConfig
import com.dicoding.trashup.ui.signin.SigninDriverViewModel
import com.google.gson.Gson
import okhttp3.Address
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterUserViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> = _isSuccess

    private val _message = MutableLiveData<String>()
    val message : LiveData<String> = _message

    fun registerUser(name: String, email: String, password: String, address: String, birth: String, phone: String) {
        _isLoading.value = true
        val requestBody = mapOf(
            "name" to name,
            "email" to email,
            "password" to password,
            "birth" to birth,
            "phone" to phone,
            "address" to address,
        )
        val client = ApiConfig.getAuthApiService().registerUser(requestBody)
        client.enqueue(object : Callback<RegisterUserResponse> {
            override fun onResponse(
                call: Call<RegisterUserResponse>,
                response: Response<RegisterUserResponse>
            ) {
                _isLoading.value = true
                if (response.isSuccessful) {
                    _isSuccess.value = true
                } else {
                    _isSuccess.value = false
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

            override fun onFailure(call: Call<RegisterUserResponse>, t: Throwable) {
                _isLoading.value = true
                _isSuccess.value = false
                Log.e(TAG, "Onfailure: ${t.message}")
            }

        })
    }

    companion object {
        const val TAG = "RegisterUserViewModel"
    }

}