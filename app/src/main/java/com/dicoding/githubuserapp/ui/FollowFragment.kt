package com.dicoding.githubuserapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {
    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    private lateinit var binding: FragmentFollowBinding
    private var position = 0
    private var username = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).toString()
        }
        if (position == 1) {
            binding.textView.text = "Get Followers"
        } else {
            binding.textView.text = "Get Following"
        }
    }
}