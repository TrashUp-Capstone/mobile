package com.dicoding.trashup.ui.user.history

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.trashup.ui.user.history.activity.ActivityUserFragment
import com.dicoding.trashup.ui.user.history.inprocess.MapsUserFragment

class SectionUserPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 1
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
//            0 -> fragment = MapsUserFragment()
            0 -> fragment = ActivityUserFragment()
        }
        return fragment as Fragment
    }

}