package com.dicoding.trashup.ui.driver.pickup


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.trashup.R
import com.dicoding.trashup.data.network.response.ResponseItem
import com.dicoding.trashup.data.network.response.driver.DataPickUpUser
import com.dicoding.trashup.databinding.FragmentPickupDriverBinding
import com.dicoding.trashup.ui.ViewModelFactory
import com.dicoding.trashup.ui.driver.home.HomeActivityDriver
import com.dicoding.trashup.ui.driver.home.HomeDriverViewModel
import com.dicoding.trashup.ui.driver.pickup.detailpickup.DetailPickUpActivity


class PickupDriverFragment : Fragment() {

    private var _binding: FragmentPickupDriverBinding? = null
    private val binding get() = _binding
    private var token: String = ""
    private val driverViewModel: HomeDriverViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity().application)
    }
    private val viewModel: PickUpViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPickupDriverBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        @Suppress("DEPRECATION")
        activity?.window?.statusBarColor = resources.getColor(R.color.light_teal)

        viewModel.getSession().observe(requireActivity()) {
            token = it.token.toString()
            viewModel.showAvailablePickup(token)
        }
        viewModel.listPickup.observe(viewLifecycleOwner) {
            setListPickupData(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }


        val layoutManager = LinearLayoutManager(requireActivity())
        binding?.AvailablePickupRv?.layoutManager = layoutManager
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.INVISIBLE
        }
    }

    private fun setListPickupData(reviewAvailablePickup: List<DataPickUpUser>) {
        val adapter = binding?.AvailablePickupRv?.adapter as? ReviewAvailablePickupAdapter
        if (adapter == null) {
            val newAdapter = ReviewAvailablePickupAdapter()
            newAdapter.submitList(reviewAvailablePickup)
            binding?.AvailablePickupRv?.adapter = newAdapter
            newAdapter.setOnItemClickCallback(object : ReviewAvailablePickupAdapter.OnItemClickCallback{
                override fun onItemClicked(data: DataPickUpUser) {
                    val intentToDetail = Intent(requireContext(), DetailPickUpActivity::class.java)
                    intentToDetail.putExtra(EXTRA_NAME, data.name)
                    intentToDetail.putExtra(EXTRA_ADDRESS, data.address)
                    intentToDetail.putExtra(EXTRA_WEIGHTS, data.totalWeight.toDouble())
                    intentToDetail.putExtra(EXTRA_ID, data.id)
                    Log.e("PickupDriverFragment", "Total Weight = ${data.totalWeight.toDouble()}")
                    startActivity(intentToDetail)
                }
            })
        } else {
            adapter.submitList(reviewAvailablePickup)
        }
    }

    override fun onPause() {
        super.onPause()

        // Kembalikan warna status bar ke warna default saat fragment di-pause
        @Suppress("DEPRECATION")
        activity?.window?.statusBarColor = resources.getColor(R.color.white)
        activity?.window?.decorView?.systemUiVisibility = 0
    }

    override fun onResume() {
        super.onResume()
        @Suppress("DEPRECATION")
        activity?.window?.statusBarColor = resources.getColor(R.color.light_teal)
    }

    companion object {
        private const val EXTRA_NAME =  "extra_name"
        private const val EXTRA_WEIGHTS = "extra_weights"
        private const val EXTRA_ADDRESS = "extra_address"
        private const val EXTRA_ID = "extra_id"
    }
}