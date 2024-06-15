package com.dicoding.trashup.ui.register.registeruser

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.trashup.R
import com.dicoding.trashup.databinding.ActivityPersonalDataUserBinding
import com.dicoding.trashup.ui.welcome.WelcomeActivity

class PersonalDataUserActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPersonalDataUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityPersonalDataUserBinding.inflate(layoutInflater)
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
                Intent(this@PersonalDataUserActivity, WelcomeActivity::class.java)
                    .apply {
                        // Menambahkan flag untuk menghapus tumpukan kembali (back stack)
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    })
            finish()
        }
    }
}