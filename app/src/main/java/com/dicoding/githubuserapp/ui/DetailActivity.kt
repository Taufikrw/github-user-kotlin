package com.dicoding.githubuserapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubuserapp.R
import com.dicoding.githubuserapp.data.Result
import com.dicoding.githubuserapp.data.response.DetailUserResponse
import com.dicoding.githubuserapp.database.Favorite
import com.dicoding.githubuserapp.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>() {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getStringExtra(USER_DATA)
        detailViewModel.setDetailUser(user.toString())

        supportActionBar?.hide()

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.detailUser.observe(this) {detailUser ->
            setDetailUser(detailUser)
        }

        val fabFavorite = binding.fabFavorite
        detailViewModel.getFavUserByUsername(user.toString()).observe(this) {
            if (it == null) {
                fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        fabFavorite.context,
                        R.drawable.ic_wishlist
                    )
                )
                isFav = false
            } else {
                fabFavorite.setImageDrawable(
                    ContextCompat.getDrawable(
                        fabFavorite.context,
                        R.drawable.ic_wishlisted
                    )
                )
                isFav = true
            }
        }

        binding.fabFavorite.setOnClickListener {
            if (isFav) {
                detailViewModel.delete(user.toString())
            } else {
                detailViewModel.insertUser(user.toString())
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = user.toString()
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = if (position == 0) "Followers" else "Following"
        }.attach()
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

    companion object {
        const val USER_DATA = "user_data"
        var isFav = false
    }
}
