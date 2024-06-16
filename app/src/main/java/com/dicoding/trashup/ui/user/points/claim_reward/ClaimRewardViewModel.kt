package com.dicoding.trashup.ui.user.points.claim_reward

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.trashup.data.UserRepository
import com.dicoding.trashup.data.network.response.user.VoucherItem
import kotlinx.coroutines.launch

class ClaimRewardViewModel(private val repository: UserRepository) : ViewModel() {
    private val _listVoucher = MutableLiveData<List<VoucherItem>?>()
    val listVoucher: LiveData<List<VoucherItem>?> = _listVoucher

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getListVoucher()
    }
    fun getListVoucher() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getVoucherList()
                _listVoucher.value = response.data
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