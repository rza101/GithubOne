package com.rhezarijaya.githubone.ui.activity.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rhezarijaya.githubone.databinding.ActivityFavoriteBinding
import com.rhezarijaya.githubone.ui.activity.detail.DetailActivity
import com.rhezarijaya.githubone.ui.adapter.UserDetailListAdapter
import com.rhezarijaya.githubone.utils.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var userDetailListAdapter: UserDetailListAdapter

    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Favorite"
        }

        userDetailListAdapter = UserDetailListAdapter {
            startActivity(Intent(this, DetailActivity::class.java).apply {
                putExtra(DetailActivity.USER_DETAIL_KEY, it)
            })
        }

        binding.apply {
            rvUser.apply {
                adapter = userDetailListAdapter
                layoutManager = LinearLayoutManager(this@FavoriteActivity)
            }

            favoriteViewModel.getFavoriteUsers().observe(this@FavoriteActivity) {
                userDetailListAdapter.submitList(it)
                tvNoData.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}