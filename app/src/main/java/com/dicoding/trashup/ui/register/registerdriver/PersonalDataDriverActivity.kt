package com.dicoding.trashup.ui.register.registerdriver

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.trashup.R
import com.dicoding.trashup.databinding.ActivityPersonalDataDriverBinding
import com.dicoding.trashup.ui.register.registeruser.PersonalDataUserActivity
import com.dicoding.trashup.ui.welcome.WelcomeActivity
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PersonalDataDriverActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonalDataDriverBinding
    private val viewModel by viewModels<RegisterDriverViewModel>()
    private val sDF = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

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

        val name = intent.getStringExtra(EXTRA_NAME).toString()
        val email = intent.getStringExtra(EXTRA_EMAIL).toString()
        val password = intent.getStringExtra(EXTRA_PASSWORD).toString()

        viewModel.isSuccess.observe(this) {
            if (it) {
                showToast(getString(R.string.successfully_created))
                startActivity(
                    Intent(this@PersonalDataDriverActivity, WelcomeActivity::class.java)
                        .apply {
                            // Menambahkan flag untuk menghapus tumpukan kembali (back stack)
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        })
                finish()
            }
        }

        viewModel.message.observe(this) {
            showToast(it)
        }

        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        binding.btnCalendar.setOnClickListener {
            val datePickerBuilder = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Date")

            if (binding.edRegisterDateOfBirth.text?.isNotEmpty() == true) {
                val existingDate = sDF.parse(binding.edRegisterDateOfBirth.text.toString())
                datePickerBuilder.setSelection(existingDate?.time ?: MaterialDatePicker.todayInUtcMilliseconds())
            } else {
                datePickerBuilder.setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            }

            val datePicker = datePickerBuilder.build()
            datePicker.show(supportFragmentManager, "datePicker")
            datePicker.addOnPositiveButtonClickListener {
                binding.edRegisterDateOfBirth.setText(sDF.format(Date(it).time))
            }
        }

        binding.registCreateAccountBtn.setOnClickListener {
            val phone = binding.edRegisterPhoneNumber.text.toString()
            val birth =  binding.edRegisterDateOfBirth.text.toString()
            val address = binding.edRegisterAddress.text.toString()
            val plat = binding.edRegisterVehicleNumber.text.toString()
            if (phone.isEmpty() || birth.isEmpty() || address.isEmpty() || plat.isEmpty()) {
                showToast(getString(R.string.error_empty))
            } else {
                createAccountDriver(name, email, password, address, birth, phone, plat)
            }
        }
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    fun createAccountDriver(name: String, email: String, password: String, address: String, birth: String, phone: String, plat: String) {
        viewModel.registerDriver(name, email, password, address, birth, phone, plat)
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
