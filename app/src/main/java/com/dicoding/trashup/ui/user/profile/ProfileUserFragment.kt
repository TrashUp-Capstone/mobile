package com.dicoding.trashup.ui.user.profile

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.trashup.R
import com.dicoding.trashup.databinding.FragmentProfileUserBinding
import com.dicoding.trashup.ui.ViewModelFactory
import com.dicoding.trashup.ui.driver.profile.ChangePasswordActivity
import com.dicoding.trashup.ui.driver.profile.EditProfileActivity
import com.dicoding.trashup.ui.user.main.MainViewModel

class ProfileUserFragment : Fragment() {

    private lateinit var _binding: FragmentProfileUserBinding
    private val binding get() = _binding
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnLogOutUser.setOnClickListener {
                viewModel.deleteSession()
            }
            btnEditProfile.setOnClickListener {
                startActivity(Intent(requireContext(), EditProfileActivity::class.java))
            }
            btnResetUserPw.setOnClickListener {
                startActivity(Intent(requireContext(), ChangePasswordActivity::class.java))
            }
        }
    }

    companion object {
        fun newInstance() = ProfileUserFragment()
    }
}