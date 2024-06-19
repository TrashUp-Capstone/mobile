package com.dicoding.trashup.ui.driver.profile.edit

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.trashup.R
import com.dicoding.trashup.databinding.ActivityEditProfileBinding
import com.dicoding.trashup.formatDateProfile
import com.dicoding.trashup.ui.ViewModelFactory
import com.dicoding.trashup.ui.driver.home.HomeDriverViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private val viewModel by viewModels<EditProfileViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val homeViewModel by viewModels<HomeDriverViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val sDF = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        supportActionBar?.hide()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getDataDriver()

        binding.apply {
            btnCalendar.setOnClickListener {
                val datePickerBuilder = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select Date")

                if (edChangeDateOfBirth.text?.isNotEmpty() == true) {
                    val existingDate = sDF.parse(edChangeDateOfBirth.text.toString())
                    datePickerBuilder.setSelection(existingDate?.time ?: MaterialDatePicker.todayInUtcMilliseconds())
                } else {
                    datePickerBuilder.setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                }

                val datePicker = datePickerBuilder.build()
                datePicker.show(supportFragmentManager, "datePicker")
                datePicker.addOnPositiveButtonClickListener {
                    edChangeDateOfBirth.setText(sDF.format(Date(it).time))
                }
            }

            saveAccountDriverBtn.setOnClickListener {
                val password = edChangePassword.text.toString()
                if (password.isEmpty()) {
                    showToast(getString(R.string.error_password))
                } else {
                    viewModel.editUser(
                        edChangeNameUser.text.toString(),
                        edChangeEmailUser.text.toString(),
                        edChangePhoneNumber.text.toString(),
                        edChangeDateOfBirth.text.toString(),
                        edChangePlate.text.toString(),
                        edChangeAddress.text.toString(),
                        edChangePassword.text.toString())
                }
            }
        }

        viewModel.message.observe(this) { message ->
            showToast(message)
            // Optionally, you can finish the activity after successful edit
            if (message == "User updated successfully") {
                finish()
            }
        }
    }

    private fun getDataDriver() {
        homeViewModel.getSession().observe(this) {user ->
            if (user.token != null) {
                homeViewModel.getDataDriver(user.token)
            }
        }
        homeViewModel.resultDriver.observe(this) {user ->
            if (user != null) {
                binding.apply {
//                    val formattedDate = formatDate(user.birth.toString())
                    val formattedDate = formatDateProfile(user.birth)
                    edChangeNameUser.setText(user.name)
                    edChangeEmailUser.setText(user.email)
                    edChangePhoneNumber.setText(user.phone)
                    edChangePlate.setText(user.licensePlate)
                    edChangeDateOfBirth.setText(formattedDate)
                    edChangeAddress.setText(user.address)
                }
            }
        }
    }

    private fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show()
    }
}