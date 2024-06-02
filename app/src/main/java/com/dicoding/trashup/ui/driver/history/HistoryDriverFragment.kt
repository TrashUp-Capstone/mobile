package com.dicoding.trashup.ui.driver.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.trashup.R
import com.dicoding.trashup.databinding.FragmentHistoryDriverBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class HistoryDriverFragment : Fragment() {

    private var _binding: FragmentHistoryDriverBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryDriverBinding.inflate(inflater, container, false)
        return binding?.root

        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = binding?.viewPagerHistory
        val tabLayout = binding?.historyTabs

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        viewPager?.adapter = sectionsPagerAdapter

        if (tabLayout != null) {
            if (viewPager != null) {
                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text =  "Tab ${position + 1}"
                    if (position == 0) {
                        tab.text = getString(R.string.in_process)
                    } else {
                        tab.text = getString(R.string.activity)
                    }
                }.attach()

                tabLayout.setTabTextColors(
                    resources.getColor(R.color.dark_grey, null),
                    resources.getColor(R.color.light_teal, null)
                )
            }
        }
    }

}