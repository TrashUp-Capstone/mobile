package com.dicoding.trashup.ui.register.registeruser

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.trashup.R
import com.dicoding.trashup.databinding.ActivityRegisterUserAccountBinding

class RegisterUserAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterUserAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterUserAccountBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()

        binding.registNextBtn.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            val confimPassword = binding.edConfirmRegisterPassword.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confimPassword.isEmpty()) {
                showToast(getString(R.string.error_empty))
            }
            else if (!password.equals(confimPassword)) {
                showToast(getString(R.string.passwords_do_not_match))
            } else {
                val intent = Intent(this@RegisterUserAccountActivity, PersonalDataUserActivity::class.java)
                intent.putExtra(EXTRA_NAME, name)
                intent.putExtra(EXTRA_PASSWORD, password)
                intent.putExtra(EXTRA_EMAIL, email)
                startActivity(intent)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val  EXTRA_EMAIL = "extra_email"
        const val EXTRA_PASSWORD = "extra_password"
    }
}