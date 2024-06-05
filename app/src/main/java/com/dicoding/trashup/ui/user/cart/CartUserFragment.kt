package com.dicoding.trashup.ui.user.cart

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.trashup.MappingHelper
import com.dicoding.trashup.R
import com.dicoding.trashup.data.db.WasteAdapter
import com.dicoding.trashup.data.db.WasteHelper
import com.dicoding.trashup.data.entity.Waste
import com.dicoding.trashup.databinding.FragmentCartUserBinding
import com.dicoding.trashup.databinding.FragmentHomeUserBinding
import com.dicoding.trashup.ui.user.camera.CameraActivity
import com.dicoding.trashup.ui.user.waiting.WaitingActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CartUserFragment : Fragment() {

    private var _binding: FragmentCartUserBinding? = null
    private val viewModel: CartUserViewModel by viewModels()
    private val binding get() = _binding!!
    private lateinit var adapter: WasteAdapter


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
            deleteAllData()
            confrimActivity()
        }
        adapter = WasteAdapter(requireContext())
        binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCart.setHasFixedSize(true)

        loadWasteDataAsync()
        return binding.root
    }

    private fun deleteAllData() {
        lifecycleScope.launch {
            val wasteHelper = WasteHelper.getInstance(requireContext()) // Use requireContext() for fragment
            wasteHelper.open()

            try {
                val deletedRowCount = wasteHelper.deleteAllData()
                if (deletedRowCount.toString().isEmpty()) {
                    Log.i("CartUserFragment", "Successfully deleted $deletedRowCount waste items")
                    adapter.listWaste.clear() // Clear existing data in the adapter
                    showToast("Waste items deleted successfully")

                } else {
                    Log.d("CartUserFragment", "No waste items found to delete")
                    showToast("No waste items found")
                }
            } finally {
                wasteHelper.close() // Ensure database is closed even in case of exceptions
            }
        }
    }

    private fun loadWasteDataAsync() {
        lifecycleScope.launch {
            val wasteHelper = WasteHelper.getInstance(requireContext()) // Use requireContext() for fragment
            wasteHelper.open()
            val deferredWastes = this.async(Dispatchers.IO) {
                val cursor = wasteHelper.queryAll()
                val wastes = MappingHelper.mapCursorToArrayList(cursor) // Assuming Waste class exists
                wastes
            }

            val wastes = deferredWastes.await()
            if (wastes.size > 0) {
                Log.e("CartUserFragment", wastes.size.toString())
                adapter.listWaste = wastes
                binding.rvCart.adapter = adapter
            } else {
                binding.rvCart.adapter = null
                showToast("No waste items found")
            }
            wasteHelper.close()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
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

    override fun onResume() {
        super.onResume()
        loadWasteDataAsync()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}