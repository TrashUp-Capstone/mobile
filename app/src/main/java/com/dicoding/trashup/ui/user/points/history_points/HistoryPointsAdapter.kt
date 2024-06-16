package com.dicoding.trashup.ui.user.points.history_points

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.trashup.data.network.response.user.HistoryVoucherItem
import com.dicoding.trashup.databinding.ItemHistoryPointsBinding
import com.dicoding.trashup.formatDate
import java.text.NumberFormat
import java.util.Locale

class HistoryPointsAdapter : ListAdapter<HistoryVoucherItem, HistoryPointsAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: ItemHistoryPointsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userVoucher: HistoryVoucherItem) {
            binding.apply {
                val formattedDate = formatDate(userVoucher.createdAt.toString())
                val formattedCost = userVoucher.voucher?.cost?.let {
                    NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(it)
                } ?: "N/A"
                tvCostReward.text = formattedCost
                tvCardDate.text = formattedDate
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemHistoryPointsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userVoucher = getItem(position)
        holder.bind(userVoucher)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryVoucherItem>() {
            override fun areItemsTheSame(oldItem: HistoryVoucherItem, newItem: HistoryVoucherItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: HistoryVoucherItem, newItem: HistoryVoucherItem): Boolean {
                return oldItem == newItem
            }

        }
    }
}