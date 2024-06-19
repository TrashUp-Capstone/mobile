package com.dicoding.trashup.ui.driver.home
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.trashup.R
import com.dicoding.trashup.data.network.response.driver.DataPickUpUser
import com.dicoding.trashup.databinding.HomeAvailableReviewBinding
import com.dicoding.trashup.formatDate


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

    class MyViewHolder(val binding: HomeAvailableReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val context = binding.root.context

        fun bind(review: DataPickUpUser) {
            binding.dateAvalaiablePickupTv.text = formatDate(review.createdAt)
            binding.nameAvailablePickupTv.text = review.name
            binding.addressUserAvailablePickup.text = review.address
            binding.weightWasteAvailablePickupTv.text = context.getString(R.string.card_weight, review.totalWeight.toDouble())

            // Uncomment and complete the Glide implementation as needed
            // Glide.with(binding.root)
            //     .load("${review.imageUrl}") // Replace with actual image URL field from review
            //     .apply(RequestOptions().transform(CircleCrop()))
            //     .into(binding.userPhotoAvailablePickup)

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
