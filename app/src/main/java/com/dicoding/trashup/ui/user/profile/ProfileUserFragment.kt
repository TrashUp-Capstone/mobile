package com.dicoding.trashup.ui.user.profile

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.trashup.databinding.FragmentProfileUserBinding
import com.dicoding.trashup.ui.ViewModelFactory
import com.dicoding.trashup.ui.user.home.HomeViewModel
import com.dicoding.trashup.ui.user.main.MainViewModel
import com.dicoding.trashup.ui.user.profile.change_pass.UserChangePasswordActivity
import com.dicoding.trashup.ui.user.profile.edit.UserEditProfileActivity
import com.dicoding.trashup.ui.user.profile.faq.UserFaqActivity

class ProfileUserFragment : Fragment() {

    private lateinit var _binding: FragmentProfileUserBinding
    private val binding get() = _binding
    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    private val homeViewModel: HomeViewModel by viewModels {
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

        homeViewModel.getUserData()
        homeViewModel.userData.observe(viewLifecycleOwner) {user ->
            binding.apply {
                if (user != null) {
                    profileNameLabel.text = user.name.toString()
                }
            }
        }

        binding.apply {
            btnLogOutUser.setOnClickListener {
                viewModel.deleteSession()
            }
            btnEditProfile.setOnClickListener {
                startActivity(Intent(requireContext(), UserEditProfileActivity::class.java))
            }
            btnResetUserPw.setOnClickListener {
                startActivity(Intent(requireContext(), UserChangePasswordActivity::class.java))
            }
            btnHelpCenterUser.setOnClickListener {
                startActivity(Intent(requireContext(), UserFaqActivity::class.java))
            }
        }
    }

    companion object {
        fun newInstance() = ProfileUserFragment()
    }
}