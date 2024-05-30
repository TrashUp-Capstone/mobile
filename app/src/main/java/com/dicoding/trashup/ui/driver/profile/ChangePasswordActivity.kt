package com.dicoding.trashup.ui.driver.profile

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.trashup.R
import com.dicoding.trashup.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChangePasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(R.layout.activity_change_password)
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
            } else if (!newPw.equals(confirmNewPw)) {
                showToast(getString(R.string.passwords_do_not_match))
            } else {
                @Suppress("DEPRECATION")
                onBackPressed()
            }
        }
    }

    private fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }
}