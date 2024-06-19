package com.dicoding.trashup.ui.driver.home

import android.content.Intent
import android.nfc.NfcAdapter.EXTRA_ID
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.trashup.R
import com.dicoding.trashup.data.network.response.driver.DataDriver
import com.dicoding.trashup.databinding.ActivityHomeDriverBinding
import com.dicoding.trashup.ui.ViewModelFactory
import com.dicoding.trashup.ui.driver.history.activity.HistoryViewModel
import com.dicoding.trashup.ui.driver.pickup.PickUpViewModel
import com.dicoding.trashup.ui.driver.pickup.detailpickup.DetailPickUpActivity
import com.dicoding.trashup.ui.welcome.WelcomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivityDriver : AppCompatActivity() {

    private val viewModel by viewModels<HomeDriverViewModel> {
        ViewModelFactory.getInstance(this)
    }
    val availablePickupViewModel by viewModels<PickUpViewModel>{
        ViewModelFactory.getInstance(this)
    }
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

        val name : String = intent.getStringExtra(EXTRA_NAME).toString()
        val address : String = intent.getStringExtra(EXTRA_ADDRESS).toString()
        val totalWeight = intent.getDoubleExtra(EXTRA_WEIGHTS, 0.0)

        viewModel.getSession().observe(this) { user ->
            if (user.token != null) {
                viewModel.getDataDriver(user.token)
                availablePickupViewModel.showAvailablePickup(user.token)
            } else { // Kalau belum login pindahkan ke Welcome Activity
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_driver_activity_main) as NavHostFragment
        navController = navHostFragment.navController

        // Setup BottomNavigationView with NavController
        NavigationUI.setupWithNavController(binding.bottomNavigationDriver, navController)
        if (intent.getBooleanExtra("navigate_to_history", false)) {
            navigateToHistoryFragment()
        }
    }

    private fun navigateToHistoryFragment() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.navigation_home_driver, true)
            .build()
        navController.navigate(R.id.navigation_history_driver, null, navOptions)
    }

    companion object {
        private const val EXTRA_NAME =  "extra_name"
        private const val EXTRA_WEIGHTS = "extra_weights"
        private const val EXTRA_ADDRESS = "extra_address"
    }

}