package com.dicoding.trashup.ui.driver.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.trashup.data.UserModel
import com.dicoding.trashup.data.UserRepository
import com.dicoding.trashup.data.network.response.ErrorResponse
import com.dicoding.trashup.data.network.response.driver.DataDriver
import com.dicoding.trashup.data.network.response.driver.DriverResponse
import com.dicoding.trashup.data.network.retrofit.ApiConfig
import com.dicoding.trashup.ui.register.registeruser.RegisterUserViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeDriverViewModel (val repository: UserRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _resultDriver = MutableLiveData<DataDriver>()
    val resultDriver : LiveData<DataDriver> = _resultDriver

    private val _message = MutableLiveData<String>()
    val message : LiveData<String> = _message

    private val _driverName = MutableLiveData<String>()
    val driverName: LiveData<String> = _driverName

    private val _driverNumber = MutableLiveData<String>()
    val driverNumber: LiveData<String> = _driverNumber

    private val _driverLicense = MutableLiveData<String>()
    val driverLicense: LiveData<String> = _driverLicense

    fun deleteSession() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getDataDriver(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getDriverApiService(token).getDriverData()
        client.enqueue(object : Callback<DriverResponse> {
            override fun onResponse(
                call: Call<DriverResponse>,
                response: Response<DriverResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _resultDriver.value = responseBody?.data
                    _driverLicense.value = responseBody?.data?.licensePlate
                    _driverName.value = responseBody?.data?.name
                    _driverNumber.value = responseBody?.data?.phone
                } else {
                    val errorJson = response.errorBody()?.string()
                    val gson = Gson()
                    try {
                        val errorResponse = gson.fromJson(errorJson, ErrorResponse::class.java)
                        _message.value = errorResponse.message
                        Log.e(RegisterUserViewModel.TAG, "Error Message: ${errorResponse.message}")
                    } catch (e: Exception) {
                        _message.value = "Unknown error"
                        Log.e(RegisterUserViewModel.TAG, "Parsing error: ${e.message}")
                    }
                }

            }

            override fun onFailure(call: Call<DriverResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    fun getSession() : LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}