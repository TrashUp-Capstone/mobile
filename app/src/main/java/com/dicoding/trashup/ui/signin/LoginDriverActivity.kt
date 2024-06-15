package com.dicoding.trashup.ui.signin

import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.trashup.R
import com.dicoding.trashup.data.UserModel
import com.dicoding.trashup.databinding.ActivityLoginDriverBinding
import com.dicoding.trashup.ui.ViewModelFactory
import com.dicoding.trashup.ui.driver.home.HomeActivityDriver


class LoginDriverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginDriverBinding
    private val viewModel by viewModels<SigninDriverViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginDriverBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()

        binding.apply {
            loginBtn.setOnClickListener {
                val email = binding.edLoginEmail.text.toString()
                val password = binding.edLoginPassword.text.toString()
                if (email.isEmpty() || password.isEmpty()) {
                    showToast(getString(R.string.error_empty))
                }
                else {
                    viewModel.loginDriver(email, password)
                }
            }
        }

        viewModel.apply {
            isLoading.observe(this@LoginDriverActivity) {
                showLoading(it)
            }
            resultAccount.observe(this@LoginDriverActivity) {
                setupAction(it)
            }
            message.observe(this@LoginDriverActivity) {
                showToast(it)
            }
        }

    }

    private fun showLoading(it: Boolean) {
        if(it) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    fun setupAction(tokens: String) {
        if (tokens != null) {
            showToast(getString(R.string.success_login))
            viewModel.saveSession(UserModel(token = tokens, isUser = false, isDriver = true))
            startActivity(Intent(this@LoginDriverActivity, HomeActivityDriver::class.java)
                .apply {
                    // Menambahkan flag untuk menghapus tumpukan kembali (back stack)
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                })
            finish()
        }
    }

    private fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }
}