package com.dicoding.trashup.ui.user.waiting

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.trashup.R
import com.dicoding.trashup.databinding.ActivityWaitingBinding
import com.dicoding.trashup.ui.ViewModelFactory
import com.dicoding.trashup.ui.user.home.HomeViewModel

class WaitingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWaitingBinding
    private val viewModel by viewModels<WaitingViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val homeViewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var idConfirmAct: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWaitingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        homeViewModel.getUserActivities()
        // Observe user activities and update idConfirmAct
        homeViewModel.userActivities.observe(this) { activities ->
            activities?.firstOrNull()?.let { activity ->
                idConfirmAct = activity.id.toString()
            }
        }

        viewModel.message.observe(this) { message ->
            showToast(message)
        }

//        binding.btnDelivered.setOnClickListener {
//            viewModel.userFinish(idConfirmAct)
//            finish()
//        }

        binding.ivCancel.setOnClickListener {
            finish()
        }
    }

    private fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }
}