package com.dicoding.trashup.ui.driver.pickup.detailpickup

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.dicoding.trashup.R
import com.dicoding.trashup.data.network.response.DetailPickUpResponse
import com.dicoding.trashup.databinding.ActivityDetailPickUpBinding

class DetailPickUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPickUpBinding
    private val viewModel by viewModels <DetailPickUpViewModel>()
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


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = ContextCompat.getColor(this, R.color.white_bg)
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
        showDetail(id)

        viewModel.detailPickup.observe(this) {
            binding.nameUser.text = it.name
            binding.addressValue.text = it.address
            binding.weightValue.text =  getString(R.string.card_weight, it.weightWaste.toString().toDouble())
            Glide.with(this)
                .load(it.avatar)
                .apply(RequestOptions().transform(CircleCrop()))
                .into(binding.userPhoto)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }



        binding.btnAccept.setOnClickListener{
            @Suppress("DEPRECATION")
            onBackPressed()
        }

    }

    private fun showDetail(id: String) {
        viewModel.showDetailPickup(id)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.INVISIBLE
        }
    }

    companion object {
        private const val EXTRA_ID =  "extra_id"
    }

}