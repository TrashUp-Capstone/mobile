package com.dicoding.trashup.ui.driver.home
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
import com.dicoding.trashup.databinding.AvailablePickupReviewBinding
import com.dicoding.trashup.databinding.HomeAvailableReviewBinding
import com.dicoding.trashup.formatDate
import com.dicoding.trashup.ui.driver.pickup.ReviewAvailablePickupAdapter

class HomeAvailableAdapter : ListAdapter<DataPickUpUser, HomeAvailableAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: DataPickUpUser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = HomeAvailableReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = this.getItem(position)
        holder.bind(review)
        holder.itemView.setOnClickListener{onItemClickCallback.onItemClicked(review)} // ini juga
    }

    class MyViewHolder(val binding: HomeAvailableReviewBinding) : RecyclerView.ViewHolder(binding.root){
        val context = binding.root.context
        fun bind(review: DataPickUpUser) {
            binding.dateAvalaiablePickupTv.text = formatDate(review.createdAt)
            binding.nameAvailablePickupTv.text = review.id
            binding.addressUserAvailablePickup.text = "${review.latitude} ${review.longitude}"
            binding.weightWasteAvailablePickupTv.text =review.totalWeight.toString()

//            Glide.with(binding.root)
//                .load("${review.}")
//                .apply(RequestOptions().transform(CircleCrop()))
//                .into(binding.userPhotoAvailablePickup)
//            binding.detailBtn.setOnClickListener {
//                onItemClickCallback.onItemClicked(review)
//                    }
        }
    }
    companion object {
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
