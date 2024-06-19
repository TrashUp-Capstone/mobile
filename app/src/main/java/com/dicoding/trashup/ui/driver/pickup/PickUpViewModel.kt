package com.dicoding.trashup.ui.driver.pickup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.trashup.data.UserModel
import com.dicoding.trashup.data.UserRepository
import com.dicoding.trashup.data.network.response.driver.DataPickUpUser
import com.dicoding.trashup.data.network.response.driver.PickUpUserResponse
import com.dicoding.trashup.data.network.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PickUpViewModel(val repository: UserRepository) : ViewModel() {
    private val _listPickup = MutableLiveData<List<DataPickUpUser>>()
    val listPickup : LiveData<List<DataPickUpUser>> = _listPickup

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private var _driverToken = MutableLiveData<String?>()
    val driverToken: LiveData<String?> = _driverToken

    init {
        showAvailablePickup(driverToken.value.toString())
    }

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
    fun getSession() : LiveData<UserModel> {
        _driverToken.value = repository.getSession().asLiveData().value?.token
        return repository.getSession().asLiveData()
    }

    companion object {
        private const val TAG = "PickUpViewModel"
    }
}