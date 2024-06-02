package com.dicoding.trashup.ui.driver.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.trashup.R
import com.dicoding.trashup.data.network.response.ResponseItem
import com.dicoding.trashup.databinding.FragmentHomeDriverBinding
import com.dicoding.trashup.databinding.FragmentPickupDriverBinding
import com.dicoding.trashup.ui.driver.pickup.DetailPickUpActivity
import com.dicoding.trashup.ui.driver.pickup.ReviewAvailablePickupAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeDriverFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeDriverFragment : Fragment() {

    private var _binding: FragmentHomeDriverBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeDriverBinding.inflate(inflater, container, false)
        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        @Suppress("DEPRECATION")

        val activity = activity as HomeActivityDriver
        val availablePickupViewmodel =activity.availablePickupViewModel
        availablePickupViewmodel.listPickup.observe(viewLifecycleOwner) {
            setListPickupData(it)
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding?.listAvailableHomeRv?.layoutManager = layoutManager
    }

    private fun setListPickupData(reviewAvailablePickup: List<ResponseItem>) {
        activity?.window?.statusBarColor = resources.getColor(R.color.white_bg)
        val adapter = HomeAvailableAdapter()
        adapter.submitList(reviewAvailablePickup)
        binding?.listAvailableHomeRv?.adapter = adapter
        adapter.setOnItemClickCallback(object : HomeAvailableAdapter.OnItemClickCallback{
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



}