package com.dicoding.trashup.ui.driver.profile.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.trashup.data.UserRepository
import com.dicoding.trashup.data.network.response.driver.DriverEditResponse
import kotlinx.coroutines.launch

class EditProfileViewModel(val repository: UserRepository) : ViewModel() {
    private val _editResponse = MutableLiveData<DriverEditResponse>()
    val editResponse: MutableLiveData<DriverEditResponse> = _editResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun editUser(
        name: String,
        email: String,
        phone: String,
        birth: String,
        licensePlate: String,
        address: String,
        password: String,
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.updateDriverProfile(
                    name,
                    email,
                    phone,
                    birth,
                    licensePlate,
                    address,
                    password)
                _editResponse.value = response
                _message.value = response.message
                _isLoading.value = false
            } catch (e: Exception) {
                e.printStackTrace()
                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}