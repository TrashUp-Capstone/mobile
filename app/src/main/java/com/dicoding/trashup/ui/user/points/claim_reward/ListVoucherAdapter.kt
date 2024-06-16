package com.dicoding.trashup.ui.user.points.claim_reward

import android.content.Intent
import android.graphics.Movie
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.trashup.data.entity.Voucher
import com.dicoding.trashup.data.network.response.user.VoucherItem
import com.dicoding.trashup.databinding.ItemRewardBinding
import com.dicoding.trashup.formatDate
import java.text.NumberFormat
import java.util.Locale

class ListVoucherAdapter : ListAdapter<VoucherItem, ListVoucherAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: ItemRewardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(voucher: VoucherItem) {
            val formattedPoint = NumberFormat.getNumberInstance(Locale("id", "ID")).format(voucher.cost)
            // Konversi price ke format rupiah
            val formattedPrice = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(voucher.cost)
            binding.apply {
                tvCostReward.text = formattedPoint
                tvValueReward.text = formattedPrice
                tvValidReward.text = formatDate(voucher.createdAt.toString())

                root.setOnClickListener {
                    val context = it.context
                    val intent = Intent(context, DetailRewardActivity::class.java).apply {
                        putExtra(EXTRA_VOUCHER_ID, voucher.id)
                        putExtra(EXTRA_VOUCHER_NAME, voucher.name)
                        putExtra(EXTRA_VOUCHER_DESCRIPTION, voucher.description)
                        putExtra(EXTRA_VOUCHER_COST, voucher.cost)
                        putExtra(EXTRA_VOUCHER_CREATED_AT, voucher.createdAt.toString())
                    }
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRewardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val voucher = getItem(position)
        holder.bind(voucher)
    }

    companion object {
        const val EXTRA_VOUCHER_ID = "extra_voucher_id"
        const val EXTRA_VOUCHER_NAME = "extra_voucher_name"
        const val EXTRA_VOUCHER_DESCRIPTION = "extra_voucher_description"
        const val EXTRA_VOUCHER_COST = "extra_voucher_cost"
        const val EXTRA_VOUCHER_CREATED_AT = "extra_voucher_created_at"

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<VoucherItem>() {
            override fun areItemsTheSame(oldItem: VoucherItem, newItem: VoucherItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: VoucherItem, newItem: VoucherItem): Boolean {
                return oldItem == newItem
            }

        }
    }


//    class ListViewHolder(var binding: ItemRewardBinding): RecyclerView.ViewHolder(binding.root)
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): ListViewHolder {
//        val binding =ItemRewardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ListViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
//        val (point, price, date) = listPoints[position]
//        val formattedPoint = NumberFormat.getNumberInstance(Locale("id", "ID")).format(point)
//// Konversi price ke format rupiah
//        val formattedPrice = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(price)
//        holder.binding.tvCostReward.text = formattedPoint
//        holder.binding.tvValueReward.text = formattedPrice
//        holder.binding.tvValidReward.text = date
//    }
}