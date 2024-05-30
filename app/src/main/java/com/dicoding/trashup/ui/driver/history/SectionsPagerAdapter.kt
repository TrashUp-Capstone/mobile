package com.dicoding.trashup.ui.driver.history

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.trashup.ui.driver.history.activity.ActivityDriverFragment
import com.dicoding.trashup.ui.driver.history.inprocess.MapsActivityFragment

class SectionsPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = MapsActivityFragment()
            1 -> fragment = ActivityDriverFragment()
        }
        return fragment as Fragment
    }
}