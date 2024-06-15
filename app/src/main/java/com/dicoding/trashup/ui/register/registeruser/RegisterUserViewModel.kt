package com.dicoding.trashup.ui.register.registeruser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterUserViewModel: ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _resultAccount = MutableLiveData<String>()
    val resultAccount: LiveData<String> = _resultAccount

    private val _message = MutableLiveData<String>()
    val message : LiveData<String> = _message

}