package com.dicoding.trashup.ui.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.trashup.R
import com.dicoding.trashup.data.UserModel
import com.dicoding.trashup.databinding.ActivityLoginUserBinding
import com.dicoding.trashup.ui.ViewModelFactory
import com.dicoding.trashup.ui.user.main.MainActivity

class LoginUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginUserBinding
    private val viewModel by viewModels<SigninViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginUserBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()

        binding.loginBtn.setOnClickListener {
            binding.apply {
                val email  = edLoginEmail.text.toString()
                val password = edLoginPassword.text.toString()
                if (email.isEmpty() || password.isEmpty()) {
                    showToast(getString(R.string.error_empty))
                } else {
                    viewModel.loginUser(email, password)
                }
            }
        }

        viewModel.apply {
            isLoading.observe(this@LoginUserActivity) {
                showLoading(it)
            }

            resultAccount.observe(this@LoginUserActivity) { token ->
                setUser(token)
            }

            message.observe(this@LoginUserActivity) {
                showToast(it)
            }
        }
    }

    private fun showLoading(it: Boolean) {
        if (it) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun setUser(token: String) {
        if (token != null) {
            viewModel.saveSession(UserModel(token, isUser = true, isDriver = false))
            startActivity(Intent(this@LoginUserActivity, MainActivity::class.java)
                .apply {
                    // Menambahkan flag untuk menghapus tumpukan kembali (back stack)
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                })
            finish()
        }
    }

    private fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show()
    }

}