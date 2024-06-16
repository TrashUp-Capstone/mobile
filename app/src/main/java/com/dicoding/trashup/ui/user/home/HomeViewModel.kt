package com.dicoding.trashup.ui.user.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.trashup.data.UserRepository
import com.dicoding.trashup.data.network.response.user.DataActivities
import com.dicoding.trashup.data.network.response.user.UserData
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: UserRepository) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userData = MutableLiveData<UserData?>()
    val userData: LiveData<UserData?> = _userData

    private val _userActivities = MutableLiveData<List<DataActivities>?>()
    val userActivities: LiveData<List<DataActivities>?> = _userActivities

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
                Log.d("DEBUG COY", "getUserData: ${_userData.value}")
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}