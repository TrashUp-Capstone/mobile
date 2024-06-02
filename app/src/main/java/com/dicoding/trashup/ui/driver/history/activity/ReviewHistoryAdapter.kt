package com.dicoding.trashup.ui.driver.history.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.dicoding.trashup.data.network.response.ResponseItem
import com.dicoding.trashup.databinding.ActivityDriverReviewBinding
import com.dicoding.trashup.databinding.AvailablePickupReviewBinding

import com.dicoding.trashup.ui.driver.pickup.ReviewAvailablePickupAdapter

class ReviewHistoryAdapter : ListAdapter<ResponseItem, ReviewHistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ActivityDriverReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = this.getItem(position)
        holder.bind(review)
    }

    class MyViewHolder(val binding: ActivityDriverReviewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(review: ResponseItem) {
            binding.datePickupDoneTv.text = review.createdAt
            binding.nameDonePickupTv.text = review.name
            binding.weightWasteAvailablePickupTv.text = review.weightWaste.toString()
            Glide.with(binding.root)
                .load("${review.avatar}")
                .apply(RequestOptions().transform(CircleCrop()))
                .into(binding.userPhotoDonePickup)
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResponseItem>() {
            override fun areContentsTheSame(oldItem: ResponseItem, newItem: ResponseItem): Boolean {
                return oldItem == newItem
            }
            override fun areItemsTheSame(oldItem: ResponseItem, newItem: ResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}