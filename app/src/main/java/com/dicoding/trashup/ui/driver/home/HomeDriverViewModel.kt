package com.dicoding.trashup.ui.driver.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.trashup.data.UserModel
import com.dicoding.trashup.data.UserRepository
import kotlinx.coroutines.launch

class HomeDriverViewModel (val repository: UserRepository) : ViewModel() {
    fun deleteSession() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getSession() : LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}