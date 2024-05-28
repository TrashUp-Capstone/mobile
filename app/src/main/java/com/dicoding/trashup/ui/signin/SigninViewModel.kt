package com.dicoding.trashup.ui.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.trashup.data.UserModel
import com.dicoding.trashup.data.UserRepository
import kotlinx.coroutines.launch

class SigninViewModel (private val repository: UserRepository): ViewModel() {
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}