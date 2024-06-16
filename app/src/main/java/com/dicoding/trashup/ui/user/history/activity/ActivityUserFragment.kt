package com.dicoding.trashup.ui.user.history.activity

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.trashup.R
import com.dicoding.trashup.data.network.response.PointsResponseItem
import com.dicoding.trashup.data.network.response.user.DataActivities
import com.dicoding.trashup.databinding.FragmentActivityUserBinding
import com.dicoding.trashup.ui.ViewModelFactory
import com.dicoding.trashup.ui.driver.history.activity.ReviewHistoryAdapter
import com.dicoding.trashup.ui.user.history.inprocess.ReviewPointsAdapter
import com.dicoding.trashup.ui.user.home.HomeViewModel

class ActivityUserFragment : Fragment() {

    private lateinit var _binding: FragmentActivityUserBinding
    private val binding get() = _binding

    private val viewModel by activityViewModels<ActivityUserViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActivityUserBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listPoints.observe(viewLifecycleOwner) {
            if (it != null) {
                setHistoryPoints(it)
            }
        }
        viewModel.getUserActivities()
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvActivityUser.layoutManager = layoutManager
    }

    private fun setHistoryPoints(reviewPoinsActivity: List<DataActivities>) {
        val adapter = ReviewPointsAdapter()
        adapter.submitList(reviewPoinsActivity)
        binding.rvActivityUser.adapter = adapter
    }
}