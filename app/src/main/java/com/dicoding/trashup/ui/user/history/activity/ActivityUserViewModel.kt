package com.dicoding.trashup.ui.user.history.activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.trashup.data.network.response.PointsResponseItem
import com.dicoding.trashup.data.network.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityUserViewModel : ViewModel() {
    private val _listPoints = MutableLiveData<List<PointsResponseItem>>()
    val listPoints : LiveData<List<PointsResponseItem>> = _listPoints

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    init {
        showPoints()
    }

    private fun showPoints() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getPoints()
        client.enqueue(object : Callback<List<PointsResponseItem>> {
            override fun onResponse (
                call: Call<List<PointsResponseItem>>,
                response: Response<List<PointsResponseItem>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    val responseBody = response.body()
                    if(responseBody != null) {
                        _listPoints.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure:${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<List<PointsResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "ActivityUserViewModel"
    }
}