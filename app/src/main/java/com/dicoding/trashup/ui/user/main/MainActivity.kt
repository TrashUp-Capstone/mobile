package com.dicoding.trashup.ui.user.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.trashup.R
import com.dicoding.trashup.databinding.ActivityMainBinding
import com.dicoding.trashup.ui.ViewModelFactory
import com.dicoding.trashup.ui.driver.home.HomeActivityDriver
import com.dicoding.trashup.ui.user.add_waste.AddWasteActivity
import com.dicoding.trashup.ui.user.home.HomeViewModel
import com.dicoding.trashup.ui.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        supportActionBar?.hide()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.getSession().observe(this) {user ->
            if (user.token != null) {
                if (user.isDriver == true) { // Misal login akun driver langsung pindah ke halaman Home Driver
                    startActivity(Intent(this, HomeActivityDriver::class.java))
                    finish()
                } else { // Kalau login akun user, biarin aja jangan pindah halaman
                    Log.e("MainActivity", user.token)
                }
            } else { // Kalau belum login pindahkan ke Welcome Activity
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val navView: BottomNavigationView = binding.navView

        navView.setupWithNavController(navController)
    }
}