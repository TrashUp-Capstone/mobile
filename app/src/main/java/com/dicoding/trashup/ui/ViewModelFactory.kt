package com.dicoding.trashup.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.trashup.data.UserRepository
import com.dicoding.trashup.di.Injection
import com.dicoding.trashup.ui.driver.home.HomeDriverViewModel
import com.dicoding.trashup.ui.driver.profile.change_pass.ChangePasswordViewModel
import com.dicoding.trashup.ui.driver.profile.edit.EditProfileViewModel
import com.dicoding.trashup.ui.signin.SigninDriverViewModel
import com.dicoding.trashup.ui.signin.SigninViewModel
import com.dicoding.trashup.ui.user.history.activity.ActivityUserViewModel
import com.dicoding.trashup.ui.user.home.HomeViewModel
import com.dicoding.trashup.ui.user.main.MainViewModel
import com.dicoding.trashup.ui.user.points.claim_reward.ClaimRewardViewModel
import com.dicoding.trashup.ui.user.points.claim_reward.DetailRewardViewModel
import com.dicoding.trashup.ui.user.points.history_points.HistoryPointsViewModel
import com.dicoding.trashup.ui.user.profile.change_pass.UserChangePasswordViewModel
import com.dicoding.trashup.ui.user.profile.edit.UserEditProfileViewModel

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
            // Driver
            modelClass.isAssignableFrom(HomeDriverViewModel::class.java) -> {
                HomeDriverViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SigninDriverViewModel::class.java) -> {
                SigninDriverViewModel(repository) as T
            }
            modelClass.isAssignableFrom(EditProfileViewModel::class.java) -> {
                EditProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ChangePasswordViewModel::class.java) -> {
                ChangePasswordViewModel(repository) as T
            }




            // User
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ActivityUserViewModel::class.java) -> {
                ActivityUserViewModel(repository) as T
            }
            modelClass.isAssignableFrom(ClaimRewardViewModel::class.java) -> {
                ClaimRewardViewModel(repository) as T
            }
            modelClass.isAssignableFrom(HistoryPointsViewModel::class.java) -> {
                HistoryPointsViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailRewardViewModel::class.java) -> {
                DetailRewardViewModel(repository) as T
            }
            modelClass.isAssignableFrom(UserEditProfileViewModel::class.java) -> {
                UserEditProfileViewModel(repository) as T
            }
            modelClass.isAssignableFrom(UserChangePasswordViewModel::class.java) -> {
                UserChangePasswordViewModel(repository) as T
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