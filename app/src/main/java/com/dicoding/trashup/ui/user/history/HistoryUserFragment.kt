package com.dicoding.trashup.ui.user.history

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.trashup.R
import com.dicoding.trashup.databinding.FragmentHistoryUserBinding
import com.dicoding.trashup.ui.driver.history.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class HistoryUserFragment : Fragment() {


    private var _binding: FragmentHistoryUserBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = binding.viewPagerHistory
        val tabLayout = binding.historyTabs

        val sectionsPagerAdapter = SectionUserPagerAdapter(this)
        viewPager.adapter = sectionsPagerAdapter
        viewPager.isUserInputEnabled = false

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "Tab ${position + 1}"
            if (position == 0) {
                tab.text = getString(R.string.in_process)
            } else {
                tab.text = getString(R.string.activity)
            }
        }.attach()

        // Get the tab index from the arguments and set the selected tab
        val tabIndex = arguments?.getInt("TAB_INDEX", 0) ?: 0
        viewPager.setCurrentItem(tabIndex, false)

        tabLayout.setTabTextColors(
            resources.getColor(R.color.dark_grey, null),
            resources.getColor(R.color.light_teal, null)
        )
    }
}