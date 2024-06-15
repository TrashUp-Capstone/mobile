package com.dicoding.trashup.ui.user.points.claim_reward

import android.graphics.Movie
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.trashup.data.entity.Voucher
import com.dicoding.trashup.databinding.ItemRewardBinding
import java.text.NumberFormat
import java.util.Locale

class ListVoucherAdapter(private val listPoints: ArrayList<Voucher>): RecyclerView.Adapter<ListVoucherAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: ItemRewardBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding =ItemRewardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (point, price, date) = listPoints[position]
        val formattedPoint = NumberFormat.getNumberInstance(Locale("id", "ID")).format(point)
// Konversi price ke format rupiah
        val formattedPrice = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(price)
        holder.binding.tvCostReward.text = formattedPoint
        holder.binding.tvValueReward.text = formattedPrice
        holder.binding.tvValidReward.text = date
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listPoints[holder.adapterPosition])}
    }

    override fun getItemCount(): Int = listPoints.size
    interface OnItemClickCallback {
        fun onItemClicked(data: Voucher)
    }
}