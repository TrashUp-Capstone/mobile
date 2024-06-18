package com.dicoding.trashup.ui.user.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.trashup.data.UserModel
import com.dicoding.trashup.data.UserRepository
import com.dicoding.trashup.data.network.response.user.UserEditResponse
import kotlinx.coroutines.launch

class ProfileUserViewModel(val repository: UserRepository) : ViewModel() {


    // Mendapatkan sesi dari user
    fun getSession() : LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    // Log Out, menghapus session
    fun deleteSession() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}