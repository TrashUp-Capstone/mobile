package com.dicoding.trashup.ui.driver.pickup.detailpickup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.trashup.data.network.response.DetailPickUpResponse
import com.dicoding.trashup.data.network.response.driver.DataListWasteItem
import com.dicoding.trashup.data.network.response.driver.ListWastePickUpResponse
import com.dicoding.trashup.data.network.retrofit.ApiConfig
import com.dicoding.trashup.ui.driver.pickup.PickUpViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPickUpViewModel : ViewModel() {

    private val _detailPickups = MutableLiveData<List<DataListWasteItem>>()
    val detailPickups: LiveData<List<DataListWasteItem>> get() = _detailPickups

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

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

    companion object {
        private const val TAG = "DetailPickUpViewModel"
    }
}
