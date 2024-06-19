package com.dicoding.trashup.ui.driver.history.inprocess

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.trashup.data.network.response.ErrorResponse
import com.dicoding.trashup.data.network.response.driver.CompletedDeliveryResponse
import com.dicoding.trashup.data.network.response.driver.ConfirmPickUpUserResponse
import com.dicoding.trashup.data.network.response.driver.DataOnGoingUserItem
import com.dicoding.trashup.data.network.response.driver.UserGoingResponse
import com.dicoding.trashup.data.network.retrofit.ApiConfig
import com.dicoding.trashup.ui.driver.pickup.detailpickup.DetailPickUpViewModel
import com.dicoding.trashup.ui.user.cart.CartUserViewModel
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DriverInProcessViewModel: ViewModel() {

    private val _detailPickups = MutableLiveData<List<DataOnGoingUserItem>>()
    val detailPickups: LiveData<List<DataOnGoingUserItem>> get() = _detailPickups

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message : LiveData<String> = _message

    private val _isConfirm = MutableLiveData<Boolean>()
    val isConfirm: LiveData<Boolean> = _isConfirm
    fun showOnGoingUser(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getDriverApiService(token).onGoingUser()
        Log.e("DriverInProcessViewModel",  "token = ${token}")
        client.enqueue(object : Callback<UserGoingResponse> {
            override fun onResponse(
                call: Call<UserGoingResponse>,
                response: Response<UserGoingResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _isConfirm.value = true
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.e("DetailPickUpViewModel", "$response")
                        _detailPickups.value = responseBody.data
                    }
                } else {
                    _isConfirm.value = false
                    Log.e(TAG, "onFailures2:${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserGoingResponse>, t: Throwable) {
                _isLoading.value = false
                _isConfirm.value = false
                Log.e(TAG, "onFailures1: ${t.message.toString()}")
            }
        })
    }

    fun completeDeliver(token: String, id: String) {
        _isLoading.value = true
        val body = mapOf(
            "id" to id
        )
        Log.e("DriverProcessViewModel", "id = $id token = $token")
        val client = ApiConfig.getDriverApiService(token).completeDelivery(body)
        client.enqueue(object : Callback<CompletedDeliveryResponse> {

            override fun onResponse(
                call: Call<CompletedDeliveryResponse>,
                response: Response<CompletedDeliveryResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _message.value = response.body()?.message
                    showOnGoingUser(token)
                } else {
                    val errorJson = response.errorBody()?.string()
                    val gson = Gson()
                    try {
                        val errorResponse = gson.fromJson(errorJson, ErrorResponse::class.java)
                        _message.value = errorResponse.message
                        Log.e(CartUserViewModel.TAG, "Error Message: ${errorResponse.message}")
                    } catch (e: Exception) {
                        _message.value = "Unknown error"
                        Log.e(CartUserViewModel.TAG, "Parsing error: ${e.message}")
                    }
                }
            }

            override fun onFailure(call: Call<CompletedDeliveryResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailures1: ${t.message.toString()}")
            }

        })

    }

    companion object {
        const val TAG = "DriverInProcessViewModel"
    }
}