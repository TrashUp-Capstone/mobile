package com.dicoding.trashup.ui.user.main
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.trashup.data.UserModel
import com.dicoding.trashup.data.UserRepository
import kotlinx.coroutines.launch

class MainViewModel (val repository: UserRepository) : ViewModel() {

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