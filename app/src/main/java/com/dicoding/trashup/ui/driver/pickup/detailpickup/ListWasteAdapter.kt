package com.dicoding.trashup.ui.driver.pickup.detailpickup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.trashup.R
import com.dicoding.trashup.data.network.response.driver.DataListWasteItem
import com.dicoding.trashup.databinding.ItemListWasteBinding

class ListWasteAdapter : ListAdapter<DataListWasteItem, ListWasteAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemListWasteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = this.getItem(position)
        holder.bind(review)

    }

    class MyViewHolder(val binding: ItemListWasteBinding) : RecyclerView.ViewHolder(binding.root){
        val context = binding.root.context
        fun bind(review: DataListWasteItem) {
            binding.tvWasteWeight.text = context.getString(R.string.card_weight, review.weight.toDouble())
            binding.tvWasteName.text = review.photo
        }
    }
    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataListWasteItem>() {
            override fun areContentsTheSame(oldItem: DataListWasteItem, newItem: DataListWasteItem): Boolean {
                return oldItem == newItem
            }
            override fun areItemsTheSame(oldItem: DataListWasteItem, newItem: DataListWasteItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}