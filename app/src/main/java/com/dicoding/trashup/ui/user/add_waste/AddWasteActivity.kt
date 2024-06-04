package com.dicoding.trashup.ui.user.add_waste

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.trashup.R
import com.dicoding.trashup.databinding.ActivityAddWasteBinding

class AddWasteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddWasteBinding

    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddWasteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Retrieve the image URI from the intent
        currentImageUri = intent.getStringExtra(EXTRA_IMAGE_URI)?.toUri()

        // Display the image if the URI is not null
        currentImageUri?.let {
            binding.previewImageView.setImageURI(it)
        }

        binding.btnSubmit.setOnClickListener {
            // Set result to indicate successful submission
            val resultIntent = Intent().apply {
                putExtra(EXTRA_NAVIGATE_TO_CART, true)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_NAVIGATE_TO_CART = "extra_navigate_to_cart"
    }
}