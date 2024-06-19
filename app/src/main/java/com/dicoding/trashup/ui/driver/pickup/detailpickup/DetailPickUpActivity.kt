package com.dicoding.trashup.ui.driver.pickup.detailpickup

import android.content.Intent
import android.nfc.NfcAdapter.EXTRA_ID
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
import androidx.navigation.NavOptions
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
import com.dicoding.trashup.ui.driver.home.HomeActivityDriver
import com.dicoding.trashup.ui.driver.home.HomeDriverViewModel
import com.dicoding.trashup.ui.driver.pickup.PickupDriverFragment
import com.dicoding.trashup.ui.driver.pickup.ReviewAvailablePickupAdapter

class DetailPickUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPickUpBinding
    private val viewModel: DetailPickUpViewModel by viewModels()
    private var token = ""
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
        val name : String = intent.getStringExtra(EXTRA_NAME).toString()
        val address : String = intent.getStringExtra(EXTRA_ADDRESS).toString()
        val totalWeight = intent.getDoubleExtra(EXTRA_WEIGHTS, 0.0)
        binding.nameUser.text = name
        binding.weightValue.text =  getString(R.string.card_weight, totalWeight)
        binding.addressValue.text = address

        viewModel.detailPickups.observe(this) { detailPickups ->
            detailPickups?.let {
                setAdapter(it)
            } ?: run {
                Log.e("DetailPickUpActivity", "detailPickups is null")
            }
        }
        viewModelMain.getSession().observe(this) { it ->
            token = it.token.toString()
            viewModel.showDetailPickup(token, id)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.listWasteRv.layoutManager = layoutManager

        window.navigationBarColor = ContextCompat.getColor(this, R.color.white_bg)
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR

        viewModel.apply {
            isLoading.observe(this@DetailPickUpActivity) {
                showLoading(it)
            }
            message.observe(this@DetailPickUpActivity) {
                showToast(it)
            }
            isConfirm.observe(this@DetailPickUpActivity) {
                if (it) {
                    val intent = Intent(this@DetailPickUpActivity, HomeActivityDriver::class.java).apply {
                        putExtra("navigate_to_history", true)
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(intent)
                    finish()
                }
            }
        }


        binding.btnAccept.setOnClickListener{
            viewModel.confirmUser(token, id)
        }

    }

    private fun showToast(it: String) {
        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
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
        private const val EXTRA_NAME =  "extra_name"
        private const val EXTRA_WEIGHTS = "extra_weights"
        private const val EXTRA_ADDRESS = "extra_address"
        private const val EXTRA_ID = "extra_id"
    }

}