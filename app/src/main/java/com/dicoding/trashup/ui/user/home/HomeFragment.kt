package com.dicoding.trashup.ui.user.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.trashup.R
import com.dicoding.trashup.data.network.response.PointsResponseItem
import com.dicoding.trashup.data.network.response.user.DataActivities
import com.dicoding.trashup.databinding.FragmentHomeUserBinding
import com.dicoding.trashup.ui.ViewModelFactory
import com.dicoding.trashup.ui.user.camera.CameraActivity
import com.dicoding.trashup.ui.user.history.activity.ActivityUserViewModel
import com.dicoding.trashup.ui.user.history.inprocess.ReviewPointsAdapter
import com.dicoding.trashup.ui.user.main.MainViewModel
import com.dicoding.trashup.ui.user.points.PointsUserActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class HomeFragment : Fragment() {

    private val viewModel by activityViewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private val viewModelMain by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private var  lat: Double? = null
    private var lon: Double? = null
    private var token: String? = null
    private lateinit var fusedLocation: FusedLocationProviderClient
    // Viewmodel buat menampilkan points
    private lateinit var binding: FragmentHomeUserBinding




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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // fusedLoaction is used to get lat lon user
        fusedLocation = LocationServices.getFusedLocationProviderClient(requireContext())

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        viewModel.messageCreate.observe(requireActivity()) {
            showToast(it)
        }
        viewModelMain.getSession().observe(requireActivity()) {
            token = it.token.toString()
        }
        requestLocation()

        binding.btnAddWaste.setOnClickListener {
            requestLocation()
            if (lat != null && lon != null  && token != null) { // if lat lon null, app cannot start the camera
                viewModel.setLocation(lat!!, lon!!) // dimasukkan ke dalam viewmodel biar lat lon bisa digunakan lagi di user driver
                viewModel.createWasteActivity(token.toString(), lat!!, lon!!)
                startCamera()
                Log.d("HomeFragment", "lat = ${lat} lon = ${lon}")
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.navigation_home_user, true)
                    .build()
                findNavController().navigate(R.id.navigation_cart_user, null, navOptions)
            } else { // show error if lat lon is null
                showToast("Error lat lon cannot null")
            }
        }

        binding.btnViewPoints.setOnClickListener {
            val intent = Intent(requireContext(), PointsUserActivity::class.java)
            startActivity(intent)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                // Tampilkan placeholder atau loading spinner
                showLoading()
            } else {
                // Sembunyikan placeholder atau loading spinner
                hideLoading()
            }
        }

        // Data user
        viewModel.userData.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                binding.apply {
                    homeUserWelcome.text = requireContext().getString(R.string.hi_message, user.name)
                    tvUserPoints.text = user.points.toString()
                }
            }
        }
        viewModel.getUserData()

        // Menampilkan list point di home
        viewModel.userActivities.observe(viewLifecycleOwner) {
            if (it != null) {
                setHistoryPoints(it)
            }
        }
        viewModel.getUserActivities()

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvRecentWaste.layoutManager = layoutManager
        binding.rvRecentWaste.isNestedScrollingEnabled = false;
    }

    private fun requestLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE
            )
            showToast("Location permission is needed for accessing location.")
        } else {
            getMyLocation()
        }
    }

    private fun getMyLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        fusedLocation.lastLocation.addOnSuccessListener { mylocation ->
            lat = mylocation.latitude
            lon = mylocation.longitude
            Log.e("Homeragment", "lat = ${lat} lon = ${lon}")
        }
    }

    private fun showToast(message: String) {
        if (isAdded) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
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
    }

    private fun setHistoryPoints(userActivities: List<DataActivities>) {
        val latestPoints = userActivities.takeLast(3)
        val adapter = ReviewPointsAdapter()
        adapter.submitList(latestPoints)
        binding.rvRecentWaste.adapter = adapter
    }

    private fun showLoading() {
        // Tampilkan placeholder atau loading spinner
        binding.homeUserWelcome.text = getString(R.string.loading_data)
        binding.tvUserPoints.text = ""
    }

    private fun hideLoading() {
        // Sembunyikan placeholder atau loading spinner
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
        private const val REQUEST_CODE = 101
    }
}