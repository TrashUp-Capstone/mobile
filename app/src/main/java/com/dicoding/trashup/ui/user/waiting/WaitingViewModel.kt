package com.dicoding.trashup.ui.user.waiting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.trashup.data.UserRepository
import com.dicoding.trashup.data.network.response.user.UserConfirmResponse
import kotlinx.coroutines.launch

class WaitingViewModel(val repository: UserRepository) : ViewModel() {
    private val _messageResponse = MutableLiveData<UserConfirmResponse>()
    val messageResponse: LiveData<UserConfirmResponse> = _messageResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    fun userFinish(id: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.userConfirmAct(id)
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