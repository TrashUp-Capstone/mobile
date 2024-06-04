package com.dicoding.trashup.ui.driver.history.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.trashup.R
import com.dicoding.trashup.data.network.response.ResponseItem
import com.dicoding.trashup.databinding.FragmentActivityDriverBinding
import com.dicoding.trashup.databinding.FragmentHistoryDriverBinding
import com.dicoding.trashup.databinding.FragmentPickupDriverBinding
import com.dicoding.trashup.ui.driver.home.HomeActivityDriver
import com.dicoding.trashup.ui.driver.pickup.ReviewAvailablePickupAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [ActivityDriverFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ActivityDriverFragment : Fragment() {
    private var _binding: FragmentActivityDriverBinding? = null
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentActivityDriverBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = activity as HomeActivityDriver
        val activityHistoryViewModel =activity.activityHistoryDriverViewModel
        activityHistoryViewModel.listPickup.observe(viewLifecycleOwner) {
            setListActivityHistoryData(it)
        }

        activityHistoryViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding?.activityDriverRv?.layoutManager = layoutManager
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.INVISIBLE
        }
    }

    private fun setListActivityHistoryData(reviewHistoryActivity: List<ResponseItem>?) {
        val adapter = ReviewHistoryAdapter()
        adapter.submitList(reviewHistoryActivity)
        binding?.activityDriverRv?.adapter = adapter
    }
}