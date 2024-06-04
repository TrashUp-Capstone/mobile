package com.dicoding.trashup.ui.user.history.activity

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.trashup.R
import com.dicoding.trashup.databinding.FragmentActivityUserBinding

class ActivityUserFragment : Fragment() {

    private lateinit var _binding: FragmentActivityUserBinding
    private val binding get() = _binding

    private val viewModel: ActivityUserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActivityUserBinding.inflate(inflater, container, false)
        return binding.root
    }
}