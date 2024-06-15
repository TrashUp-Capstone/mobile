package com.dicoding.trashup.ui.register.registerdriver

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.trashup.R
import com.dicoding.trashup.databinding.ActivityPersonalDataDriverBinding
import com.dicoding.trashup.ui.welcome.WelcomeActivity

class PersonalDataDriverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonalDataDriverBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalDataDriverBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.hide()

        binding.registCreateAccountBtn.setOnClickListener {
            startActivity(
                Intent(this@PersonalDataDriverActivity, WelcomeActivity::class.java)
                    .apply {
                        // Menambahkan flag untuk menghapus tumpukan kembali (back stack)
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    })
            finish()
        }
    }
}