package com.dicoding.trashup.ui.driver.pickup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.trashup.data.network.response.AvailablePickUpResponse
import com.dicoding.trashup.data.network.response.ResponseItem
import com.dicoding.trashup.data.network.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PickUpViewModel : ViewModel() {
    private val _listPickup = MutableLiveData<List<ResponseItem>>()
    val listPickup : LiveData<List<ResponseItem>> = _listPickup

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    init {
        showAvailablePickup()
    }

    private fun showAvailablePickup() {
        _isLoading.value = true
        val client =ApiConfig.getApiService().getAvailablePickup()
        client.enqueue(object : Callback<List<ResponseItem>> {
            override fun onResponse (
                call: Call<List<ResponseItem>>,
                response: Response<List<ResponseItem>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if(responseBody != null) {
                        _listPickup.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure:${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<List<ResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "PickUpViewModel"
    }
}