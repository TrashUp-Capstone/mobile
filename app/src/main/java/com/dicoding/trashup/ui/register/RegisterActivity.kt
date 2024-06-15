package com.dicoding.trashup.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.trashup.R
import com.dicoding.trashup.databinding.ActivityRegisterBinding
import com.dicoding.trashup.ui.register.registerdriver.RegisterDriverAccountActivity
import com.dicoding.trashup.ui.register.registeruser.RegisterUserAccountActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {
            btnCreateUser.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, RegisterUserAccountActivity::class.java))
            }
            btnCreateDriver.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, RegisterDriverAccountActivity::class.java))
            }
        }
    }
}