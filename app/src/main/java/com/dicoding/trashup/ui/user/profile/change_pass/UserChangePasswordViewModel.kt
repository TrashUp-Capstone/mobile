package com.dicoding.trashup.ui.user.profile.change_pass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.trashup.data.UserRepository
import com.dicoding.trashup.data.network.response.user.UserChangePasswordResponse
import kotlinx.coroutines.launch

class UserChangePasswordViewModel(val repository: UserRepository) : ViewModel() {
    private val _passwordResponse = MutableLiveData<UserChangePasswordResponse>()
    private val passwordResponse: LiveData<UserChangePasswordResponse> = _passwordResponse

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun updateUserPassword(password: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.updateUserPassword(password)
                _passwordResponse.value = response
                _message.value = response.message
                _isLoading.value = false
            } catch (e: Exception) {
                e.printStackTrace()
                _message.value = e.message
                _isLoading.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }
}