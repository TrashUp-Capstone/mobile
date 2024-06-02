package com.dicoding.trashup.ui.driver.pickup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.dicoding.trashup.data.network.response.ResponseItem
import com.dicoding.trashup.databinding.AvailablePickupReviewBinding

class ReviewAvailablePickupAdapter : ListAdapter<ResponseItem, ReviewAvailablePickupAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: ResponseItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = AvailablePickupReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = this.getItem(position)
        holder.bind(review)
        holder.itemView.setOnClickListener{onItemClickCallback.onItemClicked(review)} // ini juga
    }

    class MyViewHolder(val binding: AvailablePickupReviewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(review: ResponseItem) {
            binding.dateAvalaiablePickupTv.text = review.createdAt
            binding.nameAvailablePickupTv.text = review.name
            binding.addressUserAvailablePickup.text = review.address
            binding.weightWasteAvailablePickupTv.text = review.weightWaste.toString()
            Glide.with(binding.root)
                .load("${review.avatar}")
                .apply(RequestOptions().transform(CircleCrop()))
                .into(binding.userPhotoAvailablePickup)
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