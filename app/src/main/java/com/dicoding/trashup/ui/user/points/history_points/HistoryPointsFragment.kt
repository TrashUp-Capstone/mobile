package com.dicoding.trashup.ui.user.points.history_points

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.trashup.R
import com.dicoding.trashup.data.network.response.user.HistoryVoucherItem
import com.dicoding.trashup.databinding.FragmentHistoryPointsBinding
import com.dicoding.trashup.ui.ViewModelFactory

class HistoryPointsFragment : Fragment() {

    private lateinit var binding: FragmentHistoryPointsBinding

    private val viewModel by activityViewModels<HistoryPointsViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserHistoryVoucher()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryPointsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listHistoryVoucher.observe(viewLifecycleOwner) {
            if (it != null) {
                setHistoryVoucher(it)
            }
        }
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvHistoryPoints.layoutManager = layoutManager
    }

    private fun setHistoryVoucher(historyVoucherItem: List<HistoryVoucherItem>) {
        val adapter = HistoryPointsAdapter()
        adapter.submitList(historyVoucherItem)
        binding.rvHistoryPoints.adapter = adapter
        viewModel.getUserHistoryVoucher()
    }
}