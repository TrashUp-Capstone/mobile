package com.dicoding.trashup.ui.user.points.history_points

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.trashup.data.UserRepository
import com.dicoding.trashup.data.network.response.user.HistoryVoucherItem
import kotlinx.coroutines.launch

class HistoryPointsViewModel(private val repository: UserRepository) : ViewModel() {
    private val _listHistoryVoucher = MutableLiveData<List<HistoryVoucherItem>?>()
    val listHistoryVoucher: LiveData<List<HistoryVoucherItem>?> = _listHistoryVoucher

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getUserHistoryVoucher()
    }

    fun getUserHistoryVoucher() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getUserVoucherList()
                val vouchers = repository.getVoucherList().data
                val historyItems = response.data?.map { historyItem ->
                    historyItem.copy(
                        voucher = vouchers?.find { it.id == historyItem.voucherTypeId }
                    )
                }
                _listHistoryVoucher.value = historyItems
                _isLoading.value = false
            } catch (e: Exception) {
                e.printStackTrace()
                _isLoading.value = false
            } finally {
                _isLoading
            }
        }
    }
}