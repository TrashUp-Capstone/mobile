package com.dicoding.trashup.data.db

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.trashup.R
import com.dicoding.trashup.data.entity.Waste
import com.dicoding.trashup.databinding.ItemAddWasteBinding

class WasteAdapter(private val context: Context) : RecyclerView.Adapter<WasteAdapter.WasteViewHolder>() {

    private var sumpoints: Int = 0
    private var sumWeight: Double = 0.0
    var listWaste = ArrayList<Waste>()
        set(listWaste) {
            if (listWaste.size > 0) {
                this.listWaste.clear()
            }
            this.listWaste.addAll(listWaste)
            calculateWeightPoint()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WasteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_add_waste, parent, false)
        return WasteViewHolder(view)
    }
    override fun onBindViewHolder(holder: WasteViewHolder, position: Int) {
        holder.bind(listWaste[position])
    }
    override fun getItemCount(): Int = this.listWaste.size

    private fun removeItem(position: Int) {
        val wasteHelper = WasteHelper.getInstance(context)
        wasteHelper.open()
        val waste = listWaste[position]
        // Hapus data dari database
        val deletedRows = wasteHelper.deleteById(waste.id.toString())
        if (deletedRows > 0) {
            // Hapus item dari list dan memberi tahu adapter
            sumpoints -= this.listWaste.get(position).points
            sumWeight -= this.listWaste.get(position).weight
            this.listWaste.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, this.listWaste.size)
        } else {
            // Tampilkan pesan kesalahan jika penghapusan gagal
            Toast.makeText(context, "Gagal menghapus item dari database", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteAll() {
        sumpoints = 0
        sumWeight = 0.0
        val size = this.listWaste.size
        this.listWaste.clear()
        notifyItemRangeRemoved(0, size) // More efficient notification
    }

    private fun calculateWeightPoint() {
        sumpoints = listWaste.sumOf { it.points }
        sumWeight = listWaste.sumOf { it.weight }
    }

    fun getPoints(): Int = sumpoints
    fun getWeights(): Double = sumWeight

    inner class WasteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemAddWasteBinding.bind(itemView)
        fun bind(waste: Waste) {
            binding.ivWaste.setImageURI(waste.photo.toUri())
            binding.tvWasteName.text = waste.typeWaste
            binding.tvWasteWeight.text = context.getString(R.string.weight_waste, waste.weight.toDouble())
            binding.ivDismis.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    removeItem(position)
                    Log.d("WasteAdapter", "Total Points : ${sumpoints}, Total Weight = ${sumWeight}")
                }
            }
            Log.d("WasteAdapter", "Total Points : ${sumpoints}, Total Weight = ${sumWeight}")
            binding.tvPoints.text = context.getString(R.string.item_points, waste.points.toString().toInt())
        }
    }
}