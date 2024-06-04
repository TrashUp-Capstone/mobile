package com.dicoding.trashup.ui.user.cart

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.dicoding.trashup.R
import com.dicoding.trashup.databinding.FragmentCartUserBinding
import com.dicoding.trashup.databinding.FragmentHomeUserBinding
import com.dicoding.trashup.ui.user.camera.CameraActivity
import com.dicoding.trashup.ui.user.waiting.WaitingActivity

class CartUserFragment : Fragment() {

    private var _binding: FragmentCartUserBinding? = null
    private val viewModel: CartUserViewModel by viewModels()
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartUserBinding.inflate(inflater, container, false)

        binding.btnAddWaste.setOnClickListener {
            startCamera()
        }

        binding.btnConfirm.setOnClickListener {
            confrimActivity()
        }

        return binding.root
    }

    private fun confrimActivity() {
        val intent = Intent(requireContext(), WaitingActivity::class.java)
        startActivity(intent)
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
}