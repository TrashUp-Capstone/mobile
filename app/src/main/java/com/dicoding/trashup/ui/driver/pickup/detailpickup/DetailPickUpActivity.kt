package com.dicoding.trashup.ui.driver.pickup.detailpickup

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.dicoding.trashup.R
import com.dicoding.trashup.data.network.response.DetailPickUpResponse
import com.dicoding.trashup.data.network.response.driver.DataListWasteItem
import com.dicoding.trashup.data.network.response.driver.DataPickUpUser
import com.dicoding.trashup.databinding.ActivityDetailPickUpBinding
import com.dicoding.trashup.ui.ViewModelFactory
import com.dicoding.trashup.ui.driver.home.HomeDriverViewModel
import com.dicoding.trashup.ui.driver.pickup.PickupDriverFragment
import com.dicoding.trashup.ui.driver.pickup.ReviewAvailablePickupAdapter

class DetailPickUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPickUpBinding
    private val viewModel: DetailPickUpViewModel by viewModels()
    private val viewModelMain by viewModels<HomeDriverViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPickUpBinding.inflate(layoutInflater)
        this.enableEdgeToEdge()
        supportActionBar?.hide()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id : String = intent.getStringExtra(EXTRA_ID).toString()
        val totalWeight = intent.getDoubleExtra(EXTRA_WEIGHTS, 0.0)
        binding.nameUser.text = id
        binding.weightValue.text =  getString(R.string.card_weight, totalWeight)

        viewModel.detailPickups.observe(this) { detailPickups ->
            detailPickups?.let {
                Toast.makeText(this, "HALOOO INI JALAN GA", Toast.LENGTH_SHORT).show()
                setAdapter(it)
            } ?: run {
                Log.e("DetailPickUpActivity", "detailPickups is null")
            }
        }
        viewModelMain.getSession().observe(this) { it ->
            Log.e("DetailPickUpActivity", it.token.toString() + "HALOOOO")
            viewModel.showDetailPickup(it.token.toString(), id)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.listWasteRv.layoutManager = layoutManager

        window.navigationBarColor = ContextCompat.getColor(this, R.color.white_bg)
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        binding.btnAccept.setOnClickListener{
            @Suppress("DEPRECATION")
            onBackPressed()
        }

    }

    private fun setAdapter(reviewAdapter: List<DataListWasteItem>) {
        val adapter = binding.listWasteRv.adapter as? ListWasteAdapter
        if (adapter == null) {
            val newAdapter = ListWasteAdapter()
            newAdapter.submitList(reviewAdapter)
            binding.listWasteRv.adapter = newAdapter
        } else {
            adapter.submitList(reviewAdapter)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }


    companion object {
        private const val EXTRA_ID =  "extra_id"
        private const val EXTRA_WEIGHTS = "extra_weights"
    }

}