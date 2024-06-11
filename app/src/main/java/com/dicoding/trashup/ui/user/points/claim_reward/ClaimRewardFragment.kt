package com.dicoding.trashup.ui.user.points.claim_reward

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.trashup.R

class ClaimRewardFragment : Fragment() {

    companion object {
        fun newInstance() = ClaimRewardFragment()
    }

    private val viewModel: ClaimRewardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_claim_reward, container, false)
    }
}