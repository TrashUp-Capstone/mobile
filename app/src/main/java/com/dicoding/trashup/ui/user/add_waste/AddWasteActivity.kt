package com.dicoding.trashup.ui.user.add_waste

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.trashup.R
import com.dicoding.trashup.data.db.DatabaseContract
import com.dicoding.trashup.data.db.WasteHelper
import com.dicoding.trashup.data.entity.Waste
import com.dicoding.trashup.databinding.ActivityAddWasteBinding

class AddWasteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddWasteBinding
    private lateinit var wasteHelper: WasteHelper
    private var waste: Waste?= null
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
        wasteHelper = WasteHelper.getInstance(applicationContext)
        wasteHelper.open()

        // Retrieve the image URI from the intent
        currentImageUri = intent.getStringExtra(EXTRA_IMAGE_URI)?.toUri()

        // Display the image if the URI is not null
        currentImageUri?.let {
            binding.previewImageView.setImageURI(it)
        }

        val wasteTypes = resources.getStringArray(R.array.waste_types)
        val menuTextAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, wasteTypes)
        binding.edWasteType.setAdapter(menuTextAdapter)
        binding.edWasteType.setSelection(wasteTypes.size - 1)


        binding.btnSubmit.setOnClickListener {
            val type = binding.edWasteType.selectedItem.toString()
            val weight = binding.edGarbageWeight.text.toString()

            if (type.isEmpty() || weight.isEmpty() || currentImageUri == null || type == "Nothing") {
                showToast(getString(R.string.error_empty))
            } else {
                // Set result to indicate successful submission
                addWaste(type, currentImageUri.toString().toUri(), weight.toDouble())
                val resultIntent = Intent().apply {
                    putExtra(EXTRA_NAVIGATE_TO_CART, true)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }

        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@AddWasteActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun addWaste(typeWaste: String, photo: Uri, weightWaste: Double) {
        waste?.typeWaste = typeWaste
        waste?.weight = weightWaste
        waste?.photo = photo.toString()

        val values = ContentValues()
        values.put(DatabaseContract.WasteColumns.TYPEWASTE, typeWaste)
        values.put(DatabaseContract.WasteColumns.WEIGHT, weightWaste)
        values.put(DatabaseContract.WasteColumns.PHOTO, photo.toString())

        // Memasukkan nilai-nilai ke dalam database
        val result = wasteHelper.insert(values)

        if (result > 0) {
            waste?.id = result.toInt()
            setResult(RESULT_ADD, intent)
            finish()
        } else {
            Toast.makeText(
                this@AddWasteActivity,
                "Gagal menambah data",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_NAVIGATE_TO_CART = "extra_navigate_to_cart"
        const val RESULT_ADD = 101
    }
}