package com.dicoding.trashup.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.trashup.R
import com.dicoding.trashup.databinding.ActivityChooseSideBinding
import com.dicoding.trashup.ui.driver.home.HomeActivityDriver
import com.dicoding.trashup.ui.user.main.MainActivity
import com.dicoding.trashup.ui.user.main.MainViewModel
import com.dicoding.trashup.ui.welcome.WelcomeActivity

class ChooseSideActivity : AppCompatActivity() {

    private lateinit var binding : ActivityChooseSideBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        binding = ActivityChooseSideBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.getSession().observe(this) {user ->
            if (user.token != null) {
                if (user.isDriver == true) { // Misal login akun driver langsung pindah ke halaman Home Driver
                    startActivity(Intent(this, HomeActivityDriver::class.java))
                    finish()
                } else { // Kalau login akun user, pindah ke halaman user
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            } else { // Kalau belum login pindahkan ke Welcome Activity
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
    }
}