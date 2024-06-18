package com.dicoding.trashup.ui.user.profile.change_pass

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.trashup.R
import com.dicoding.trashup.databinding.ActivityUserChangePasswordBinding
import com.dicoding.trashup.ui.ViewModelFactory
import com.dicoding.trashup.ui.user.home.HomeViewModel

class UserChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserChangePasswordBinding
    private val viewModel by viewModels<UserChangePasswordViewModel> {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.saveNewPasswordBtn.setOnClickListener {
            val currentPw = binding.edCurrentPassword.text.toString()
            val newPw = binding.edNewPassword.text.toString()
            val confirmNewPw = binding.edConfirmNewPassword.text.toString()
            if (currentPw.isEmpty() || newPw.isEmpty() || confirmNewPw.isEmpty()) {
                showToast(getString(R.string.error_empty))
            } else if (newPw != confirmNewPw) {
                showToast(getString(R.string.passwords_do_not_match))
            } else {
                viewModel.updateUserPassword(newPw)
                showToast(getString(R.string.password_user_changed))
                finish()
            }
        }
    }

    private fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }
}