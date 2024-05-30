package com.dicoding.trashup.ui.signin

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.trashup.R
import com.dicoding.trashup.databinding.ActivitySigninBinding

private lateinit var binding: ActivitySigninBinding
class SigninActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()


        binding.apply {
            btnSigninDriver.setOnClickListener {
                startActivity(Intent(this@SigninActivity, LoginDriverActivity::class.java))
            }
            btnSigninUser.setOnClickListener {
                startActivity(Intent(this@SigninActivity, LoginUserActivity::class.java))
            }
        }

    }
}