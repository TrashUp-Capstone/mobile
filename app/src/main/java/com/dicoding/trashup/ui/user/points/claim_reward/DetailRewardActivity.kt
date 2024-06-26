package com.dicoding.trashup.ui.user.points.claim_reward

import android.graphics.Movie
import android.media.tv.TvContract.Programs.Genres.MOVIES
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.viewModels
import com.dicoding.trashup.R
import com.dicoding.trashup.data.entity.Voucher
import com.dicoding.trashup.databinding.ActivityDetailRewardBinding
import com.dicoding.trashup.formatDate
import com.dicoding.trashup.ui.ViewModelFactory
import com.dicoding.trashup.ui.user.home.HomeViewModel
import java.text.NumberFormat
import java.util.Locale

class DetailRewardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailRewardBinding
    private val viewModel by viewModels<DetailRewardViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val homeViewModel : HomeViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private  var myPoints : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailRewardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val voucherId = intent.getIntExtra(ListVoucherAdapter.EXTRA_VOUCHER_ID, 0)
        val voucherName = intent.getStringExtra(ListVoucherAdapter.EXTRA_VOUCHER_NAME)
        val voucherDescription = intent.getStringExtra(ListVoucherAdapter.EXTRA_VOUCHER_DESCRIPTION)
        val voucherCost = intent.getIntExtra(ListVoucherAdapter.EXTRA_VOUCHER_COST, 0)
        val voucherCreatedAt = intent.getStringExtra(ListVoucherAdapter.EXTRA_VOUCHER_CREATED_AT)
        homeViewModel.getUserData()
        homeViewModel.userData.observe(this) {
            if (it?.points != null) {
                myPoints = it.points
            }
        }


        val formattedPoint = NumberFormat.getNumberInstance(Locale("id", "ID")).format(voucherCost)
        // Konversi price ke format rupiah
        val formattedPrice = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(voucherCost)
        val formattedDate = formatDate(voucherCreatedAt.toString())
        binding.apply {
            tvCostReward.text = formattedPoint
            tvValueReward.text = formattedPrice
            tvValidReward.text = formattedDate
            tvDescReward.text = voucherDescription
            btnClaim.setOnClickListener {
                if (myPoints < voucherCost) {
                    Log.d("DetailRewardActivity", "my points = " + myPoints.toString())
                    showToast(getString(R.string.points_not_sufficient))
                } else {
                    viewModel.redeemVoucher(voucherId)
                    homeViewModel.getUserData()
                    @Suppress("DEPRECATION")
                    onBackPressed()
                    finish()
                }
            }
        }

        viewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}