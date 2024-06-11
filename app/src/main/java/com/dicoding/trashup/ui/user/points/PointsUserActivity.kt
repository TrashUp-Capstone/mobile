package com.dicoding.trashup.ui.user.points

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.trashup.R
import com.dicoding.trashup.databinding.ActivityMainBinding
import com.dicoding.trashup.databinding.ActivityPointsUserBinding
import com.dicoding.trashup.ui.ViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class PointsUserActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPointsUserBinding
    private val viewModel by viewModels<PointsUserViewModel> {
        ViewModelFactory.getInstance(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPointsUserBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val viewPager = binding.viewPagerPoints
        val tabLayout = binding.pointsTabs

        val sectionsPagerAdapter = SectionPointsPagerAdapter(this)
        viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "Tab ${position + 1}"
            if (position == 0) {
                tab.text = getString(R.string.claim_reward)
            } else {
                tab.text = getString(R.string.history_points)
            }
        }.attach()

        tabLayout.setTabTextColors(
            resources.getColor(R.color.dark_grey, null),
            resources.getColor(R.color.light_teal, null)
        )
    }
}