package com.dicoding.trashup.data.network.retrofit

import com.dicoding.trashup.data.network.response.AvailablePickUpResponse
import com.dicoding.trashup.data.network.response.DetailPickUpResponse
import com.dicoding.trashup.data.network.response.PointsResponse
import com.dicoding.trashup.data.network.response.PointsResponseItem
import com.dicoding.trashup.data.network.response.ResponseItem
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("available_pickup")
    fun getAvailablePickup() : Call<List<ResponseItem>>

    @GET("available_pickup/{id}")
    fun getDetailPickup(@Path("id") id:String) : Call<DetailPickUpResponse>

    @DELETE("available_pickup/{id}")
    fun deletAvailablePickup(@Path("id") id: String) : Call<DetailPickUpResponse>

    @GET("point_users")
    fun getPoints(): Call<List<PointsResponseItem>>

}

