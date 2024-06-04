package com.dicoding.trashup.ui.driver.pickup.detailpickup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.trashup.data.network.response.DetailPickUpResponse
import com.dicoding.trashup.data.network.retrofit.ApiConfig
import com.dicoding.trashup.ui.driver.pickup.PickUpViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPickUpViewModel: ViewModel() {

    private val _detailPickup = MutableLiveData<DetailPickUpResponse>()
    val detailPickup : LiveData<DetailPickUpResponse> = _detailPickup

    private val _isloading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isloading


    fun showDetailPickup(id: String) {
        _isloading.value = true
        val client = ApiConfig.getApiService().getDetailPickup(id)
        client.enqueue(object : Callback<DetailPickUpResponse> {
            override fun onResponse(
                call: Call<DetailPickUpResponse>,
                response: Response<DetailPickUpResponse>
            ) {
                _isloading.value = false
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if(responseBody != null) {
                        _detailPickup.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure:${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<DetailPickUpResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }


        })
    }

    fun deleteAvailablePickup(id: String) {
        _isloading.value = true
        val client = ApiConfig.getApiService().deletAvailablePickup(id)
        client.enqueue(object : Callback<DetailPickUpResponse> {
            override fun onResponse(
                call: Call<DetailPickUpResponse>,
                response: Response<DetailPickUpResponse>
            ) {
                _isloading.value = false
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if(responseBody != null) {
                        _detailPickup.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure:${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<DetailPickUpResponse>, t: Throwable) {
                _isloading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }


        })
    }

    companion object {
        private const val TAG = "DetailPickUpViewModel"
    }
}