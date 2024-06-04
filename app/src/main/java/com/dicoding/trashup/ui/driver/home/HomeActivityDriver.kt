package com.dicoding.trashup.ui.driver.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.trashup.R
import com.dicoding.trashup.databinding.ActivityHomeDriverBinding
import com.dicoding.trashup.ui.ViewModelFactory
import com.dicoding.trashup.ui.driver.history.activity.HistoryViewModel
import com.dicoding.trashup.ui.driver.pickup.PickUpViewModel
import com.dicoding.trashup.ui.welcome.WelcomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivityDriver : AppCompatActivity() {

    private val viewModel by viewModels<HomeDriverViewModel> {
        ViewModelFactory.getInstance(this)
    }
    val availablePickupViewModel by viewModels<PickUpViewModel>()
    val activityHistoryDriverViewModel by viewModels<HistoryViewModel>()
    lateinit var navController: NavController

    private lateinit var binding: ActivityHomeDriverBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeDriverBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        supportActionBar?.hide()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.getSession().observe(this) {user ->
            if (user.token != null) {
               // biarin aja
            } else { // Kalau belum login pindahkan ke Welcome Activity
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_driver_activity_main) as NavHostFragment
        navController = navHostFragment.navController

        // Setup BottomNavigationView with NavController
        NavigationUI.setupWithNavController(binding.bottomNavigationDriver, navController)
    }

}