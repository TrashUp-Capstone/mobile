package com.dicoding.trashup.ui.user.profile.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.trashup.data.UserRepository
import com.dicoding.trashup.data.network.response.user.UserEditResponse
import kotlinx.coroutines.launch

class UserEditProfileViewModel(val repository: UserRepository) : ViewModel() {
    private val _messageResponse = MutableLiveData<UserEditResponse>()
    val messageResponse : LiveData<UserEditResponse> = _messageResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun editUser(
        name: String,
        email: String,
        phone: String,
        birth: String,
        address: String,
        password: String,
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.updateUserProfile(
                    name,
                    email,
                    phone,
                    birth,
                    address,
                    password)
                _messageResponse.value = response
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