package com.dicoding.trashup.ui.user.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.trashup.R
import com.dicoding.trashup.data.network.response.PointsResponseItem
import com.dicoding.trashup.databinding.FragmentHomeUserBinding
import com.dicoding.trashup.ui.ViewModelFactory
import com.dicoding.trashup.ui.user.camera.CameraActivity
import com.dicoding.trashup.ui.user.history.activity.ActivityUserViewModel
import com.dicoding.trashup.ui.user.history.inprocess.ReviewPointsAdapter
import com.dicoding.trashup.ui.user.points.PointsUserActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeUserBinding? = null
    private val viewModel by activityViewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    // Viewmodel buat menampilkan points
    private val viewModelPoints : ActivityUserViewModel by viewModels()



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

        // Data user
        viewModel.getUserData()
        viewModel.userData.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                binding.apply {
                    homeUserWelcome.text = requireContext().getString(R.string.hi_message, user.name)
                    tvUserPoints.text = user.points.toString()
                }

            }
        }

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.btnAddWaste.setOnClickListener {
            startCamera()
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.navigation_home_user, true)
                .build()
            findNavController().navigate(R.id.navigation_cart_user, null, navOptions)
        }

        binding.btnViewPoints.setOnClickListener {
            val intent = Intent(requireContext(), PointsUserActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            // Menampilkan list point di home
        viewModelPoints.listPoints.observe(viewLifecycleOwner) {
            setHistoryPoints(it)
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvRecentWaste.layoutManager = layoutManager
        binding.rvRecentWaste.isNestedScrollingEnabled = false;
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

    private fun setHistoryPoints(reviewPointsActivity: List<PointsResponseItem>) {
        val latestPoints = reviewPointsActivity.takeLast(3)
        val adapter = ReviewPointsAdapter()
        adapter.submitList(latestPoints)
        binding.rvRecentWaste.adapter = adapter
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}