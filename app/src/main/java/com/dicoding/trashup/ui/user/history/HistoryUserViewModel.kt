package com.dicoding.trashup.ui.user.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.trashup.data.network.response.PointsResponseItem
import com.dicoding.trashup.data.network.response.ResponseItem
import com.dicoding.trashup.data.network.retrofit.ApiConfig
import com.dicoding.trashup.ui.driver.history.activity.HistoryViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryUserViewModel : ViewModel() {


    companion object {
        private const val TAG = "HistoryUserViewModel"
    }
}