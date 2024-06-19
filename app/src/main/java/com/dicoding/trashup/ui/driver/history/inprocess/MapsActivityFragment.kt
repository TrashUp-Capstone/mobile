package com.dicoding.trashup.ui.driver.history.inprocess

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.dicoding.trashup.R
import com.dicoding.trashup.data.network.response.driver.DataOnGoingUserItem
import com.dicoding.trashup.databinding.FragmentMapsActivityBinding
import com.dicoding.trashup.ui.ViewModelFactory
import com.dicoding.trashup.ui.driver.home.HomeDriverViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MapsActivityFragment : Fragment() {

    private var token = ""
    var lat: Double = 0.0
    var lon: Double = 0.0
    private var id = ""
    private var gMap: GoogleMap? = null
    private val viewModel: DriverInProcessViewModel by viewModels()
    private val viewModelMain by viewModels<HomeDriverViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }
    private var _binding: FragmentMapsActivityBinding? = null
    private val binding get() = _binding!!

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
        gMap = googleMap
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        googleMap.uiSettings.isZoomControlsEnabled = false
        googleMap.uiSettings.isIndoorLevelPickerEnabled = true
        googleMap.uiSettings.isCompassEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = true
        googleMap.setPadding(0, 0, 0, 200)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsActivityBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        val bottomSheet = binding.bottomSheetDriver
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

        viewModelMain.getSession().observe(requireActivity()) {
            token = it.token.toString()
            viewModel.showOnGoingUser(token)
        }


        viewModel.detailPickups.observe(requireActivity()) { listUser ->
            showDataUser(listUser)

        }
        viewModel.message.observe(requireActivity()) {
            showToast(it)
        }
        binding.confirmDriverBtn.setOnClickListener {
            viewModel.completeDeliver(token, id)
            viewModel.showOnGoingUser(token)
        }

    }

    private fun disableView(isEnable: Boolean) {
        Log.e("MapsFragment", isEnable.toString())
        fun setViewState(view: View, isEnable: Boolean) {
            view.isEnabled = isEnable
            if (isEnable) {
                view.visibility = View.VISIBLE
            } else {
                view.visibility = View.INVISIBLE
            }
        }
        setViewState(binding.constraintLayout2, isEnable)
        setViewState(binding.bottomSheetDriver, isEnable)
        binding.tvEmpty.apply {
            isEnabled = !isEnable
            visibility = if (!isEnable) View.VISIBLE else View.INVISIBLE
            Log.d("tvEmpty", "isEnabled: ${isEnabled}, visibility: ${visibility}")
        }
    }

    private fun showToast(it: String) {
        if (isAdded && context != null) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateMapLocation(lat: Double, lon: Double) {
        val location = LatLng(lat, lon)
        gMap?.addMarker(MarkerOptions().position(location).title("User Location"))
        gMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f)) // Zoom level can be adjusted as needed
    }

    private fun showDataUser(it: List<DataOnGoingUserItem>){
        val index = it.indexOfFirst { list ->
            list.endedAt == null
        }
        if (index != -1) {
            val firstData = it[index]
            id = firstData.id
            Log.e("MENCOBA", "id = $id")
            disableView(true)
            lat = firstData.latitude
            lon = firstData.longitude
            updateMapLocation(lat, lon)
            binding.yourName.text = firstData.userId
            binding.yourPhoneNumber.text = context?.getString(R.string.card_weight, firstData.totalWeight)
            binding.yourAddres.text = "Lat = ${firstData.latitude} Lon = ${firstData.longitude}"
        } else {
            disableView(false)
            Log.e("MapsActivityFragment", "No item found with null endedAt")
        }
    }
}