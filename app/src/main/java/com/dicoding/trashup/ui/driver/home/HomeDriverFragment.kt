package com.dicoding.trashup.ui.driver.home


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.trashup.R
import com.dicoding.trashup.data.network.response.ResponseItem
import com.dicoding.trashup.data.network.response.driver.DataPickUpUser
import com.dicoding.trashup.databinding.FragmentHomeDriverBinding
import com.dicoding.trashup.ui.ViewModelFactory
import com.dicoding.trashup.ui.driver.pickup.detailpickup.DetailPickUpActivity


class HomeDriverFragment : Fragment() {

    private lateinit var binding: FragmentHomeDriverBinding
    private val driverViewModel: HomeDriverViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity().application)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeDriverBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        @Suppress("DEPRECATION")

        val activity = activity as HomeActivityDriver
        val availablePickupViewmodel =activity.availablePickupViewModel
        availablePickupViewmodel.listPickup.observe(viewLifecycleOwner) {
            setListPickupData(it)
        }

        availablePickupViewmodel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        driverViewModel.apply {
            driverNumber.observe(requireActivity()) {
                binding.driverNumber.text = it
            }
            driverName.observe(requireActivity()) {
                binding.driverName.text = it
            }
            driverLicense.observe(requireActivity()) {
                binding.driverNumberPlat.text = it
            }
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.listAvailableHomeRv.layoutManager = layoutManager
        binding.btnViewProfile.setOnClickListener {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.navigation_home_driver, true)
                .build()
            activity.navController.navigate(R.id.navigation_profile_driver, null, navOptions)
        }
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun setListPickupData(reviewAvailablePickup: List<DataPickUpUser>) {
        activity?.window?.statusBarColor = resources.getColor(R.color.white_bg)
        val adapter = HomeAvailableAdapter()
        adapter.submitList(reviewAvailablePickup)
        binding.listAvailableHomeRv.adapter = adapter
        adapter.setOnItemClickCallback(object : HomeAvailableAdapter.OnItemClickCallback{
            override fun onItemClicked(data: DataPickUpUser) {
                val intentToDetail = Intent(requireContext(), DetailPickUpActivity::class.java)
                intentToDetail.putExtra(EXTRA_ID, data.id)
                intentToDetail.putExtra(EXTRA_WEIGHTS, data.totalWeight)
                startActivity(intentToDetail)
            }
        })
    }

    override fun onPause() {
        super.onPause()
        // Kembalikan warna status bar ke warna default saat fragment di-pause
        @Suppress("DEPRECATION")
        activity?.window?.statusBarColor = resources.getColor(R.color.white)
        activity?.window?.decorView?.systemUiVisibility = 0
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    companion object {
        private const val EXTRA_ID =  "extra_id"
        private const val EXTRA_WEIGHTS = "extra_weights"
    }
}