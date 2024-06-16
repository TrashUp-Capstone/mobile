package com.dicoding.trashup.ui.user.points.claim_reward

import android.content.Intent
import android.graphics.Movie
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.trashup.R
import com.dicoding.trashup.data.entity.Voucher
import com.dicoding.trashup.data.network.response.user.VoucherItem
import com.dicoding.trashup.databinding.ActivityMainBinding
import com.dicoding.trashup.databinding.FragmentClaimRewardBinding
import com.dicoding.trashup.ui.ViewModelFactory
import com.dicoding.trashup.ui.user.home.HomeViewModel

class ClaimRewardFragment : Fragment() {

    private val viewModel: ClaimRewardViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private val homeViewModel : HomeViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private lateinit var binding: FragmentClaimRewardBinding
    private val list = ArrayList<Voucher>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        list.addAll(getListVoucher())
//        showRecycleList()
        homeViewModel.getUserData()
        homeViewModel.userData.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                binding.apply {
                    homeUserWelcome.text = requireContext().getString(R.string.hi_message, user.name)
                    tvUserPoints.text = user.points.toString()
                }
            }
        }
        viewModel.listVoucher.observe(viewLifecycleOwner) {
            if (it != null) {
                setVoucherData(it)
            }
        }
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvVoucher.layoutManager = layoutManager
    }

    private fun setVoucherData(voucherItems: List<VoucherItem>) {
        val adapter = ListVoucherAdapter()
        adapter.submitList(voucherItems)
        binding.rvVoucher.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentClaimRewardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.getUserData()
        viewModel.getListVoucher()
    }

    companion object {
        const val EXTRA_VOUCHER_ID = "extra_voucher_id"
    }

//    private fun showRecycleList() {
//        binding.rvVoucher.layoutManager = LinearLayoutManager(requireContext())
//        val listVoucherAdapter = ListVoucherAdapter(list)
//        binding.rvVoucher.adapter = listVoucherAdapter
//    }

//    private fun getListVoucher(): ArrayList<Voucher> {
//        val points = resources.getIntArray(R.array.voucher_points)
//        val date = resources.getStringArray(R.array.voucher_date)
//        val price = resources.getIntArray(R.array.voucher_price)
//        val listVoucher = ArrayList<Voucher>()
//
//        for (x in points.indices) {
//            val voucher = Voucher(points[x], price[x], date[x])
//            listVoucher.add(voucher)
//        }
//        return listVoucher
//    }


}