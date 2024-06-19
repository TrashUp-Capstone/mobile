package com.dicoding.trashup.ui.driver.history.activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.dicoding.trashup.R
import com.dicoding.trashup.data.network.response.ResponseItem
import com.dicoding.trashup.data.network.response.driver.DataOnGoingUserItem
import com.dicoding.trashup.databinding.ActivityDriverReviewBinding
import com.dicoding.trashup.databinding.AvailablePickupReviewBinding
import com.dicoding.trashup.formatDate

import com.dicoding.trashup.ui.driver.pickup.ReviewAvailablePickupAdapter

class ReviewHistoryAdapter : ListAdapter<DataOnGoingUserItem, ReviewHistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ActivityDriverReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = this.getItem(position)
        holder.bind(review)
    }

    class MyViewHolder(val binding: ActivityDriverReviewBinding) : RecyclerView.ViewHolder(binding.root){
        val context = binding.root.context
        fun bind(review: DataOnGoingUserItem) {
            binding.datePickupDoneTv.text = formatDate(review.createdAt)
            binding.nameDonePickupTv.text = review.userId
            binding.weightWasteAvailablePickupTv.text = context.getString(R.string.card_weight, review.totalWeight)
//            Glide.with(binding.root)
//                .load("${review.avatar}")
//                .apply(RequestOptions().transform(CircleCrop()))
//                .into(binding.userPhotoDonePickup)
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataOnGoingUserItem>() {
            override fun areContentsTheSame(oldItem: DataOnGoingUserItem, newItem: DataOnGoingUserItem): Boolean {
                return oldItem == newItem
            }
            override fun areItemsTheSame(oldItem: DataOnGoingUserItem, newItem: DataOnGoingUserItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}