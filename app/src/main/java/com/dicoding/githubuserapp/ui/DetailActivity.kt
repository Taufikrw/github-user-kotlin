package com.dicoding.githubuserapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.data.response.DetailUserResponse
import com.dicoding.githubuserapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    companion object {
        const val USER_DATA = "user_data"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getStringExtra(USER_DATA)
        detailViewModel.setDetailUser(user.toString())

        supportActionBar?.hide()

        detailViewModel.detailUser.observe(this) {detailUser ->
            setDetailUser(detailUser)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setDetailUser(detailUser: DetailUserResponse) {
        binding.tvUsername.text = detailUser.login
        binding.name.text = detailUser.name
        binding.tvFollowers.text = this@DetailActivity.resources.getString(R.string.followers, detailUser.followers)
        binding.tvFollowing.text = this@DetailActivity.resources.getString(R.string.following, detailUser.following)
        Glide.with(binding.root.context)
            .load(detailUser.avatarUrl)
            .into(binding.ivAvatar)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}