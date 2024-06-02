package com.dicoding.trashup.ui.user.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.trashup.R
import com.dicoding.trashup.databinding.ActivityMainBinding
import com.dicoding.trashup.ui.ViewModelFactory
import com.dicoding.trashup.ui.driver.home.HomeActivityDriver
import com.dicoding.trashup.ui.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        viewModel.getSession().observe(this) {user ->
            if (user.token != null) {
                if (user.isDriver == true) { // Misal login akun driver langsung pindah ke halaman Home Driver
                    startActivity(Intent(this, HomeActivityDriver::class.java))
                    finish()
                } else { // Kalau login akun user, biarin aja jangan pindah halaman
                    //
                }
            } else { // Kalau belum login pindahkan ke Welcome Activity
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home_user,
//                R.id.navigation_history_user,
//                R.id.navigation_cart_user,
//                R.id.navigation_profile_user,
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}