package com.dicoding.trashup.ui.user.history.inprocess

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.dicoding.trashup.R
import com.dicoding.trashup.databinding.FragmentMapsUserBinding
import com.dicoding.trashup.ui.ViewModelFactory
import com.dicoding.trashup.ui.user.home.HomeViewModel
import com.dicoding.trashup.ui.user.waiting.WaitingViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MapsUserFragment : Fragment() {

    private var _binding: FragmentMapsUserBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }
    private val waitingViewModel by viewModels<WaitingViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private lateinit var idConfirmAct: String


    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        googleMap.uiSettings.isZoomControlsEnabled = false
        googleMap.uiSettings.isIndoorLevelPickerEnabled = true
        googleMap.uiSettings.isCompassEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsUserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.user_map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        homeViewModel.getUserActivities()
        // Observe user activities and update idConfirmAct
        homeViewModel.userActivities.observe(this) { activities ->
            activities?.firstOrNull()?.let { activity ->
                idConfirmAct = activity.id.toString()
            }
        }

        waitingViewModel.message.observe(this) { message ->
            showToast(message)
        }

        binding.btnDelivered.setOnClickListener {
            waitingViewModel.userFinish(idConfirmAct)
        }

        val bottomSheet = binding.bottomSheet
        BottomSheetBehavior.from(bottomSheet).apply {
            peekHeight = 250
            this.state = BottomSheetBehavior.STATE_COLLAPSED
            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    // Handle state changes if needed
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    // Change the alpha value of the dimView based on slide offset
                    binding.dimView.visibility = View.VISIBLE
                    binding.dimView.alpha = slideOffset
                }
            })
        }
    }

    private fun showToast(string: String) {
        Toast.makeText(requireContext(), string, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}