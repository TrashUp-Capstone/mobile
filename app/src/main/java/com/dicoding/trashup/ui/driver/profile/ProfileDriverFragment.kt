package com.dicoding.trashup.ui.driver.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.dicoding.trashup.databinding.FragmentProfileDriverBinding
import com.dicoding.trashup.ui.ViewModelFactory
import com.dicoding.trashup.ui.driver.home.HomeDriverViewModel
import com.dicoding.trashup.ui.driver.profile.change_pass.ChangePasswordActivity
import com.dicoding.trashup.ui.driver.profile.edit.EditProfileActivity


class ProfileDriverFragment : Fragment() {

    private lateinit var _binding: FragmentProfileDriverBinding
    private val binding get() = _binding
    private val driverViewModel: HomeDriverViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireActivity().application)
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

        driverViewModel.driverName.observe(requireActivity()) {
            binding.profileNameLabel.text = it
        }

        binding.apply {
            btnEditProfile.setOnClickListener {
                startActivity(Intent(requireActivity(), EditProfileActivity::class.java))
            }
            btnLogOutDriver.setOnClickListener {
                driverViewModel.deleteSession()
            }
            btnResetDriverPw.setOnClickListener {
                startActivity(Intent(requireActivity(), ChangePasswordActivity::class.java))
            }
        }
    }
}