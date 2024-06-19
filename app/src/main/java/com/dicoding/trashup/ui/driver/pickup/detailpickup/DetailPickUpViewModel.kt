package com.dicoding.trashup.ui.driver.pickup.detailpickup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.trashup.data.network.response.DetailPickUpResponse
import com.dicoding.trashup.data.network.response.ErrorResponse
import com.dicoding.trashup.data.network.response.driver.ConfirmPickUpUserResponse
import com.dicoding.trashup.data.network.response.driver.DataListWasteItem
import com.dicoding.trashup.data.network.response.driver.ListWastePickUpResponse
import com.dicoding.trashup.data.network.retrofit.ApiConfig
import com.dicoding.trashup.ui.driver.pickup.PickUpViewModel
import com.dicoding.trashup.ui.user.cart.CartUserViewModel
import com.google.android.gms.common.api.Api
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPickUpViewModel : ViewModel() {

    private val _detailPickups = MutableLiveData<List<DataListWasteItem>>()
    val detailPickups: LiveData<List<DataListWasteItem>> get() = _detailPickups

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isConfirm = MutableLiveData<Boolean>()
    val isConfirm: LiveData<Boolean> = _isConfirm

    private val _message = MutableLiveData<String>()
    val message : LiveData<String> = _message

    fun showDetailPickup(token: String, id: String) {
        _isLoading.value = true
        val client = ApiConfig.getDriverApiService(token).getListWaste(id)
        Log.e("DetailPickUpViewModel", "token = ${token}, token = ${id}")
        client.enqueue(object : Callback<ListWastePickUpResponse> {
            override fun onResponse(
                call: Call<ListWastePickUpResponse>,
                response: Response<ListWastePickUpResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.e("DetailPickUpViewModel", "$response")
                        _detailPickups.value = responseBody.dataListWaste
                    }
                    Log.e(TAG, "onFailures3:${responseBody}")
                } else {
                    Log.e(TAG, "onFailures2:${response.message()}")
                }
            }

            override fun onFailure(call: Call<ListWastePickUpResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailures1: ${t.message.toString()}")
            }
        })
    }

    fun confirmUser(token: String, id: String) {
        Log.e("DetailPickUpViewModel", "Token = ${token}, id = ${id}")
        val body =  mapOf(
            "id" to id
        )
        _isLoading.value = true
        val client = ApiConfig.getDriverApiService(token).confirmUser(body)
        client.enqueue(object : Callback<ConfirmPickUpUserResponse> {
            override fun onResponse(
                call: Call<ConfirmPickUpUserResponse>,
                response: Response<ConfirmPickUpUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _isConfirm.value = true
                } else {
                    _isConfirm.value = false
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

            override fun onFailure(call: Call<ConfirmPickUpUserResponse>, t: Throwable) {
                _isLoading.value = false
                _isConfirm.value = false
                Log.e(TAG, "onFailures1: ${t.message.toString()}")
            }

        })
    }

    companion object {
        private const val TAG = "DetailPickUpViewModel"
    }
}
