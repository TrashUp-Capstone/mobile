package com.dicoding.trashup.ui.user.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.dicoding.trashup.R
import com.dicoding.trashup.databinding.FragmentHomeUserBinding
import com.dicoding.trashup.ui.ViewModelFactory
import com.dicoding.trashup.ui.user.camera.CameraActivity
import com.dicoding.trashup.ui.user.camera.CameraActivity.Companion.CAMERAX_RESULT
import com.dicoding.trashup.ui.user.main.MainViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeUserBinding? = null
    private val viewModel by activityViewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }


    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeUserBinding.inflate(inflater, container, false)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.btnAddWaste.setOnClickListener {
            startCamera()
            navigateToCart()
        }

        return binding.root
    }

    private val startCameraLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Perform any async operation here before navigating to the cart fragment
                navigateToCart()
            } else {
                Toast.makeText(requireContext(), "Camera activity was canceled", Toast.LENGTH_SHORT).show()
            }
        }


    private fun navigateToCart() {
        findNavController().navigate(R.id.navigation_cart_user)
    }


    private fun startCamera() {
        val intent = Intent(requireContext(), CameraActivity::class.java)
        startCameraLauncher.launch(intent)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}