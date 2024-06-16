package com.dicoding.trashup.ui.user.history.activity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.trashup.data.UserRepository
import com.dicoding.trashup.data.network.response.PointsResponseItem
import com.dicoding.trashup.data.network.response.user.DataActivities
import com.dicoding.trashup.data.network.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityUserViewModel(private val repository: UserRepository) : ViewModel() {
    private val _listPoints = MutableLiveData<List<DataActivities>?>()
    val listPoints : LiveData<List<DataActivities>?> = _listPoints

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun getUserActivities() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getUserActivities()
                _listPoints.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

//    private fun showPoints() {
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getPoints()
//        client.enqueue(object : Callback<List<DataActivities>> {
//            override fun onResponse (
//                call: Call<List<DataActivities>>,
//                response: Response<List<DataActivities>>
//            ) {
//                _isLoading.value = false
//                if(response.isSuccessful) {
//                    val responseBody = response.body()
//                    if(responseBody != null) {
//                        _listPoints.value = response.body()
//                    } else {
//                        Log.e(TAG, "onFailure:${response.message()}")
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<List<DataActivities>>, t: Throwable) {
//                _isLoading.value = false
//                Log.e(TAG, "onFailure: ${t.message.toString()}")
//            }
//        })
//    }

    companion object {
        private const val TAG = "ActivityUserViewModel"
    }
}