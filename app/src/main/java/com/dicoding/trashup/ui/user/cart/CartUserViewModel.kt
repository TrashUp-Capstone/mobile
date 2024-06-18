package com.dicoding.trashup.ui.user.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.trashup.data.network.response.ErrorResponse
import com.dicoding.trashup.data.network.response.user.SendWasteResponse
import com.dicoding.trashup.data.network.response.user.SubmitPhotoResponse
import com.dicoding.trashup.data.network.response.user.SubmitWasteResponse
import com.dicoding.trashup.data.network.retrofit.ApiConfig
import com.dicoding.trashup.ui.user.home.HomeViewModel
import com.google.android.gms.common.api.Api
import com.google.gson.Gson
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartUserViewModel : ViewModel() {

    private val _message = MutableLiveData<String>() // Nampilin status message pada fitur add waste
    val message: LiveData<String> = _message

    private val _messagePhoto = MutableLiveData<String>() // Nampilin status message pada fitur submit foto
    val messagePhoto: LiveData<String> = _messagePhoto

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun SubmitWaste(token: String, actId: String, wasteId: String, weight: String, photo: String) {
        _isLoading.value = true
        val desc = "nothing"
        val body = mapOf(
            "activity_id" to actId,
            "waste_type_id" to wasteId,
            "description" to desc,
            "weight" to weight,
            "photo" to photo
        )
        val client = ApiConfig.getUserApiService(token).submitListWaste(body)
        client.enqueue(object : Callback<SubmitWasteResponse> {
            override fun onResponse(
                call: Call<SubmitWasteResponse>,
                response: Response<SubmitWasteResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _message.value = response.body()?.message
                } else {
                    val errorJson = response.errorBody()?.string()
                    val gson = Gson()
                    try {
                        val errorResponse = gson.fromJson(errorJson, ErrorResponse::class.java)
                        _message.value = errorResponse.message
                        Log.e(TAG, "Error Message: ${errorResponse.message}")
                    } catch (e: Exception) {
                        _message.value = "Unknown error"
                        Log.e(TAG, "Parsing error: ${e.message}")
                    }
                }
            }

            override fun onFailure(call: Call<SubmitWasteResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure ${t.message.toString()}")
            }

        })
    }

    fun submitPhotoWaste(token: String, photo: MultipartBody.Part) {
        _isLoading.value = true
        val client = ApiConfig.getUserApiService(token).addPhoto(photo)
        client.enqueue(object : Callback<SubmitPhotoResponse> {
            override fun onResponse(
                call: Call<SubmitPhotoResponse>,
                response: Response<SubmitPhotoResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _messagePhoto.value = response.body()?.message
                } else {
                    val errorJson = response.errorBody()?.string()
                    val gson = Gson()
                    try {
                        val errorResponse = gson.fromJson(errorJson, ErrorResponse::class.java)
                        _messagePhoto.value = errorResponse.message
                        Log.e(TAG, "Error Message: ${errorResponse.message}")
                    } catch (e: Exception) {
                        _messagePhoto.value = "Unknown error"
                        Log.e(TAG, "Parsing error: ${e.message}")
                    }
                }
            }

            override fun onFailure(call: Call<SubmitPhotoResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure ${t.message.toString()}")
            }

        })
    }

    fun sendWaste(token: String, idAct: String, sumWeight: String, sumPoint: String) {
        val body = mapOf(
            "id" to idAct,
            "total_weight" to sumWeight,
            "points" to sumPoint,
        )

        val client = ApiConfig.getUserApiService(token).sendWaste(body)
        client.enqueue(object : Callback<SendWasteResponse> {
            override fun onResponse(
                call: Call<SendWasteResponse>,
                response: Response<SendWasteResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _message.value = response.body()?.message
                } else {
                    val errorJson = response.errorBody()?.string()
                    val gson = Gson()
                    try {
                        val errorResponse = gson.fromJson(errorJson, ErrorResponse::class.java)
                        _message.value = errorResponse.message
                        Log.e(TAG, "Error Message: ${errorResponse.message}")
                    } catch (e: Exception) {
                        _messagePhoto.value = "Unknown error"
                        Log.e(TAG, "Parsing error: ${e.message}")
                    }
                }
            }

            override fun onFailure(call: Call<SendWasteResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure ${t.message.toString()}")
            }

        })
    }

    companion object {
        const val TAG = "CartUserViewModel"
    }
}