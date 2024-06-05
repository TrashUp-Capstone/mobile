package com.dicoding.trashup.data.db

import android.content.Context
import android.net.Uri
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

    var listWaste = ArrayList<Waste>()
        set(listWaste) {
            if (listWaste.size > 0) {
                this.listWaste.clear()
            }
            this.listWaste.addAll(listWaste)
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WasteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_add_waste, parent, false)
        return WasteViewHolder(view)
    }
    override fun onBindViewHolder(holder: WasteViewHolder, position: Int) {
        holder.bind(listWaste[position])
    }
    override fun getItemCount(): Int = this.listWaste.size

    fun addItem(waste: Waste) {
        this.listWaste.add(waste)
        notifyItemInserted(this.listWaste.size - 1)
    }
    fun updateItem(position: Int, waste: Waste) {
        this.listWaste[position] = waste
        notifyItemChanged(position, waste)
    }
    fun removeItem(position: Int) {
        val wasteHelper = WasteHelper.getInstance(context)
        wasteHelper.open()
        val waste = listWaste[position]
        // Hapus data dari database
        val deletedRows = wasteHelper.deleteById(waste.id.toString())
        if (deletedRows > 0) {
            // Hapus item dari list dan memberi tahu adapter
            this.listWaste.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, this.listWaste.size)
        } else {
            // Tampilkan pesan kesalahan jika penghapusan gagal
            Toast.makeText(context, "Gagal menghapus item dari database", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteAll() {
        val size = this.listWaste.size
        this.listWaste.clear()
        notifyItemRangeRemoved(0, size) // More efficient notification
    }
    inner class WasteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemAddWasteBinding.bind(itemView)
        fun bind(waste: Waste) {
            binding.ivWaste.setImageURI(waste.photo.toUri())
            binding.tvWasteName.text = waste.typeWaste
            binding.tvWasteWeight.text = waste.weight.toString()
            binding.ivDismis.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    removeItem(position)
                }
            }
        }
    }
}