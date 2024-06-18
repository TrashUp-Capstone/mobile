package com.dicoding.trashup.ui.driver.pickup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.trashup.data.network.response.driver.DataPickUpUser
import com.dicoding.trashup.data.network.response.driver.PickUpUserResponse
import com.dicoding.trashup.data.network.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PickUpViewModel : ViewModel() {
    private val _listPickup = MutableLiveData<List<DataPickUpUser>>()
    val listPickup : LiveData<List<DataPickUpUser>> = _listPickup

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun showAvailablePickup(token: String) {
        _isLoading.value = true
        val client =ApiConfig.getDriverApiService(token).getPickUpUser()
        client.enqueue(object : Callback<PickUpUserResponse> {
            override fun onResponse (
                call: Call<PickUpUserResponse>,
                response: Response<PickUpUserResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if(responseBody != null) {
                        _listPickup.value = responseBody.data
                    } else {
                        Log.e(TAG, "onFailure:${response.message()} + token = $token")
                    }
                }
            }

            override fun onFailure(call: Call<PickUpUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "PickUpViewModel"
    }
}