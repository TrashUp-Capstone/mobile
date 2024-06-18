package com.dicoding.trashup.ui.user.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.trashup.data.UserRepository
import com.dicoding.trashup.data.network.response.ErrorResponse
import com.dicoding.trashup.data.network.response.user.AddWasteResponse
import com.dicoding.trashup.data.network.response.user.DataActivities
import com.dicoding.trashup.data.network.response.user.UserData
import com.dicoding.trashup.data.network.retrofit.ApiConfig
import com.dicoding.trashup.ui.register.registeruser.RegisterUserViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val repository: UserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userData = MutableLiveData<UserData?>()
    val userData: LiveData<UserData?> = _userData

    private val _userActivities = MutableLiveData<List<DataActivities>?>()
    val userActivities: LiveData<List<DataActivities>?> = _userActivities

    private val _messageCreate = MutableLiveData<String>() // Nampilin status message pada fitur add waste
    val messageCreate: LiveData<String> = _messageCreate

    private val _userToken = MutableLiveData<String?>()
    val userToken: LiveData<String?> = _userToken

    private val _latitude = MutableLiveData<Double>()
    val latitude: LiveData<Double> get() = _latitude

    private val _longitude = MutableLiveData<Double>()
    val longitude: LiveData<Double> get() = _longitude

    private val _idActivity = MutableLiveData<String>() // Menyimpan id activity yang sudah dibuat
    val idActivity : LiveData<String> = _idActivity
    init {
        getUserActivities()
    }
    fun getUserActivities() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getUserActivities()
                _userActivities.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getUserData() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getUserData()
                _userData.value = response.data
                _userToken.value = response.data?.token
                Log.d("DEBUG COY", "getUserData: ${_userData.value}")
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createWasteActivity(token: String, lat: Double, lon: Double) {
        _isLoading.value = true
        val map =  mapOf(
            "latitude" to lat,
            "longitude" to lon
        )
        val client = ApiConfig.getUserApiService(token).createActivity(map)
        client.enqueue(object : Callback<AddWasteResponse>{
            override fun onResponse(
                call: Call<AddWasteResponse>,
                response: Response<AddWasteResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                    _messageCreate.value = response.body()?.message
                    _idActivity.value = response.body()?.activityId
                } else {
                    val errorJson = response.errorBody()?.string()
                    val gson = Gson()
                    try {
                        val errorResponse = gson.fromJson(errorJson, ErrorResponse::class.java)
                        _messageCreate.value = errorResponse.message
                        Log.e(TAG, "Error Message: ${errorResponse.message}")
                    } catch (e: Exception) {
                        _messageCreate.value = "Unknown error"
                        Log.e(TAG, "Parsing error: ${e.message}")
                    }
                }
            }

            override fun onFailure(call: Call<AddWasteResponse>, t: Throwable) {
                _isLoading.value = false
                TODO("Not yet implemented")
            }
        })
    }

    fun setLocation(lat: Double, lon: Double) {
        _latitude.value = lat
        _longitude.value = lon
    }

    companion object {
        const val TAG = "HomeViewModel"
    }
}