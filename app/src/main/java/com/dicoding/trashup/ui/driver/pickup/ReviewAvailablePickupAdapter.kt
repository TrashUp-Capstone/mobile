package com.dicoding.trashup.ui.driver.pickup

import android.content.Context
import android.content.Intent
import android.nfc.NfcAdapter.EXTRA_ID
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
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
import com.dicoding.trashup.ui.driver.pickup.detailpickup.DetailPickUpActivity

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
            binding.nameAvailablePickupTv.text = review.name
            binding.addressUserAvailablePickup.text = review.address
            binding.weightWasteAvailablePickupTv.text = context.getString(R.string.card_weight, review.totalWeight.toDouble())
            binding.detailBtn.setOnClickListener {
                val intentToDetail = Intent(context, DetailPickUpActivity::class.java)
                intentToDetail.putExtra(EXTRA_NAME, review.name)
                intentToDetail.putExtra(EXTRA_ADDRESS, review.address)
                intentToDetail.putExtra(EXTRA_WEIGHTS, review.totalWeight.toDouble())
                intentToDetail.putExtra(EXTRA_ID, review.id)
                Log.e("PickupDriverFragment", "Total Weight = ${review.totalWeight.toDouble()}")
                context.startActivity(intentToDetail)
            }

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
        private const val EXTRA_NAME =  "extra_name"
        private const val EXTRA_WEIGHTS = "extra_weights"
        private const val EXTRA_ADDRESS = "extra_address"
        private const val EXTRA_ID = "extra_id"
    }

}