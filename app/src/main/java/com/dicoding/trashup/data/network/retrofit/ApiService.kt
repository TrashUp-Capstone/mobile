package com.dicoding.trashup.data.network.retrofit

import com.dicoding.trashup.data.network.response.AvailablePickUpResponse
import com.dicoding.trashup.data.network.response.ResponseItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("available_pickup")
    fun getAvailablePickup() : Call<List<ResponseItem>>

}

