package com.dicoding.trashup.ui.driver.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dicoding.trashup.R
import com.dicoding.trashup.databinding.FragmentHistoryDriverBinding
import com.dicoding.trashup.databinding.FragmentProfileDriverBinding
import com.dicoding.trashup.ui.ViewModelFactory
import com.dicoding.trashup.ui.driver.home.HomeDriverViewModel


class ProfileDriverFragment : Fragment() {

    private lateinit var _binding: FragmentProfileDriverBinding
    private val binding get() = _binding
    private val viewModel: HomeDriverViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileDriverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnEditProfile.setOnClickListener {
                startActivity(Intent(requireActivity(), EditProfileActivity::class.java))
            }
            btnLogOutDriver.setOnClickListener {
                viewModel.deleteSession()
            }
            btnResetDriverPw.setOnClickListener {
                startActivity(Intent(requireActivity(), ChangePasswordActivity::class.java))
            }
        }
    }
}