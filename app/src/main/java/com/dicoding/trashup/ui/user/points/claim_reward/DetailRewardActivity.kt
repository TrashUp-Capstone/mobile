package com.dicoding.trashup.ui.user.points.claim_reward

import android.graphics.Movie
import android.media.tv.TvContract.Programs.Genres.MOVIES
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.trashup.R
import com.dicoding.trashup.data.entity.Voucher
import com.dicoding.trashup.databinding.ActivityDetailRewardBinding
import java.text.NumberFormat
import java.util.Locale

class DetailRewardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailRewardBinding
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

        val voucher = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<Voucher>(EXTRA_VOUCHER, Voucher::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Voucher>(EXTRA_VOUCHER)
        }

        if (voucher != null) {
            val formattedPoint = NumberFormat.getNumberInstance(Locale("id", "ID")).format(voucher.points)
// Konversi price ke format rupiah
            val formattedPrice = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(voucher.price)
            binding.tvCostReward.text = formattedPrice
            binding.tvValueReward.text = formattedPoint
            binding.tvValidReward.text = voucher.date
        }
    }

    companion object{
        const val EXTRA_VOUCHER = "VOUCHER"
    }
}