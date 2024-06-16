package com.dicoding.trashup.ui.user.points.claim_reward

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.trashup.data.UserRepository
import com.dicoding.trashup.data.network.response.user.UserRedeemedVoucherResponse
import kotlinx.coroutines.launch

class DetailRewardViewModel(private val repository: UserRepository) : ViewModel() {
    private val _redeemVoucher = MutableLiveData<UserRedeemedVoucherResponse?>()
    val redeemVoucher: LiveData<UserRedeemedVoucherResponse?> = _redeemVoucher

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String?>()
    val message: LiveData<String?> = _message

    fun redeemVoucher(voucherTypeId: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.redeemVoucher(voucherTypeId)
                _redeemVoucher.value = response
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