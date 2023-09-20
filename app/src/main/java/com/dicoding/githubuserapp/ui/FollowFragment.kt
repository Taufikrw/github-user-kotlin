package com.dicoding.githubuserapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuserapp.data.response.ItemsItem
import com.dicoding.githubuserapp.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {
    private lateinit var binding: FragmentFollowBinding
    private var position = 0
    private var username = ""
    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

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

        Log.d("username:", username)
        binding.rvFollow.layoutManager = LinearLayoutManager(DetailActivity())
        val followViewModel = ViewModelProvider(this)[FollowViewModel::class.java]

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
        if (position == 1) {
            followViewModel.setFollowersUsers(username)
            followViewModel.listFollowers.observe(viewLifecycleOwner) { users ->
                setFollowersUsers(users)
            }
        } else {
            followViewModel.setFollowingUsers(username)
            followViewModel.listFollowing.observe(viewLifecycleOwner) { users ->
                setFollowingUsers(users)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setFollowersUsers(users: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(users)
        binding.rvFollow.adapter = adapter
    }

    private fun setFollowingUsers(users: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(users)
        binding.rvFollow.adapter = adapter
    }
}
