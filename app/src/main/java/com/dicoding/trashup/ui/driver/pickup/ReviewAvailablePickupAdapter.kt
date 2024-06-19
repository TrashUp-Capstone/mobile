package com.dicoding.trashup.ui.driver.pickup

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
import com.dicoding.trashup.data.network.response.driver.DataPickUpUser
import com.dicoding.trashup.data.network.response.driver.PickUpUserResponse
import com.dicoding.trashup.databinding.AvailablePickupReviewBinding
import com.dicoding.trashup.formatDate

class ReviewAvailablePickupAdapter : ListAdapter<DataPickUpUser, ReviewAvailablePickupAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: DataPickUpUser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = AvailablePickupReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, onItemClickCallback)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = this.getItem(position)
        holder.bind(review)
        holder.itemView.setOnClickListener{onItemClickCallback.onItemClicked(review)}
    }

    class MyViewHolder(val binding: AvailablePickupReviewBinding, private val onItemClickCallback: OnItemClickCallback) : RecyclerView.ViewHolder(binding.root){
        val context = binding.root.context
        fun bind(review: DataPickUpUser) {
            binding.dateAvalaiablePickupTv.text = formatDate(review.createdAt)
            binding.nameAvailablePickupTv.text = review.id
            binding.addressUserAvailablePickup.text = "${review.latitude} ${review.longitude}"
            binding.weightWasteAvailablePickupTv.text = context.getString(R.string.card_weight, review.totalWeight)

//            Glide.with(binding.root)
//                .load("${review.}")
//                .apply(RequestOptions().transform(CircleCrop()))
//                .into(binding.userPhotoAvailablePickup)
//            binding.detailBtn.setOnClickListener {
//                onItemClickCallback.onItemClicked(review)
//                    }
        }
    }
    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataPickUpUser>() {
            override fun areContentsTheSame(oldItem: DataPickUpUser, newItem: DataPickUpUser): Boolean {
                return oldItem == newItem
            }
            override fun areItemsTheSame(oldItem: DataPickUpUser, newItem: DataPickUpUser): Boolean {
                return oldItem == newItem
            }
        }
    }

}