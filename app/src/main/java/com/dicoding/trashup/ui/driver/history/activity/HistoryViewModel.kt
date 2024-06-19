package com.dicoding.trashup.ui.driver.history.activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide.init
import com.dicoding.trashup.data.network.response.ResponseItem
import com.dicoding.trashup.data.network.response.driver.DataOnGoingUserItem
import com.dicoding.trashup.data.network.response.driver.UserGoingResponse
import com.dicoding.trashup.data.network.retrofit.ApiConfig
import com.dicoding.trashup.ui.driver.history.inprocess.DriverInProcessViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel: ViewModel() {
    private val _listPickup = MutableLiveData<List<DataOnGoingUserItem>>()
    val listPickup : LiveData<List<DataOnGoingUserItem>> = _listPickup

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun showUserDone(token: String) {
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
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Log.e(TAG, "$response")
                        _listPickup.value = responseBody.data
                    }
                    Log.e(TAG, "onFailures3:${responseBody}")
                } else {
                    Log.e(TAG, "onFailures2:${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserGoingResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailures1: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "HistoryViewModel"
    }
}