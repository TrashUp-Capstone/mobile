package com.dicoding.trashup.ui.driver.pickup

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.trashup.R
import com.dicoding.trashup.data.network.response.ResponseItem
import com.dicoding.trashup.databinding.FragmentPickupDriverBinding
import com.dicoding.trashup.ui.driver.home.HomeActivityDriver


class PickupDriverFragment : Fragment() {

    private var _binding: FragmentPickupDriverBinding? = null
    private val binding get() = _binding

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

        val activity = activity as HomeActivityDriver
        val availablePickupViewmodel =activity.availablePickupViewModel
        availablePickupViewmodel.listPickup.observe(viewLifecycleOwner) {
            setListPickupData(it)
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding?.AvailablePickupRv?.layoutManager = layoutManager
    }

    private fun setListPickupData(reviewAvailablePickup: List<ResponseItem>) {
        val adapter = ReviewAvailablePickupAdapter()
        adapter.submitList(reviewAvailablePickup)
        binding?.AvailablePickupRv?.adapter = adapter
        adapter.setOnItemClickCallback(object : ReviewAvailablePickupAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ResponseItem) {
                val intentToDetail = Intent(requireContext(), DetailPickUpActivity::class.java)
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

    override fun onResume() {
        super.onResume()
        @Suppress("DEPRECATION")
        activity?.window?.statusBarColor = resources.getColor(R.color.light_teal)
    }

    companion object {
    }
}