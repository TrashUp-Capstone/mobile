package com.dicoding.trashup.ui.user.points

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.trashup.ui.user.points.claim_reward.ClaimRewardFragment
import com.dicoding.trashup.ui.user.points.history_points.HistoryPointsFragment

class SectionPointsPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = ClaimRewardFragment()
            1 -> fragment = HistoryPointsFragment()
        }
        return fragment as Fragment
    }
}