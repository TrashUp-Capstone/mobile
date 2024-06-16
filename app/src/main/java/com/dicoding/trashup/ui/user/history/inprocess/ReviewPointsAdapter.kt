package com.dicoding.trashup.ui.user.history.inprocess

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getString
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.trashup.R
import com.dicoding.trashup.data.network.response.PointsResponseItem
import com.dicoding.trashup.data.network.response.ResponseItem
import com.dicoding.trashup.data.network.response.user.DataActivities
import com.dicoding.trashup.databinding.ItemRecentWasteBinding
import com.dicoding.trashup.formatDate


class ReviewPointsAdapter : ListAdapter<DataActivities, ReviewPointsAdapter.MyViewHolder>(DIFF_CALLBACK)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRecentWasteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = this.getItem(position)
        holder.bind(review)
    }

    class MyViewHolder(val binding: ItemRecentWasteBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(review: DataActivities) {
            val context = binding.root.context
            binding.tvCardPoints.text = context.getString(R.string.card_points, review.points.toString().toInt())
            binding.tvCardDate.text = formatDate(review.createdAt.toString())
            binding.tvCardWeight.text = context.getString(R.string.card_weight, review.totalWeight.toString().toDouble())
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataActivities>() {
            override fun areContentsTheSame(oldItem: DataActivities, newItem: DataActivities): Boolean {
                return oldItem == newItem
            }
            override fun areItemsTheSame(oldItem: DataActivities, newItem: DataActivities): Boolean {
                return oldItem == newItem
            }
        }
    }

}