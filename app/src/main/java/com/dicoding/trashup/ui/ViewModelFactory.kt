package com.dicoding.trashup.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.trashup.data.UserRepository
import com.dicoding.trashup.di.Injection
import com.dicoding.trashup.ui.driver.home.HomeDriverViewModel
import com.dicoding.trashup.ui.signin.SigninDriverViewModel
import com.dicoding.trashup.ui.signin.SigninViewModel
import com.dicoding.trashup.ui.user.home.HomeViewModel
import com.dicoding.trashup.ui.user.main.MainViewModel

class ViewModelFactory(private val repository: UserRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SigninViewModel::class.java) -> {
                SigninViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HomeDriverViewModel::class.java) -> {
                HomeDriverViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SigninDriverViewModel::class.java) -> {
                SigninDriverViewModel(repository) as T
            }
            // User
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(
                        Injection.provideRepository(context)
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }
}