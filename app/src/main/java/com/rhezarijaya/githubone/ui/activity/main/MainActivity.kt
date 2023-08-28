package com.rhezarijaya.githubone.ui.activity.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.rhezarijaya.githubone.R
import com.rhezarijaya.githubone.data.network.response.UserDetailResponse
import com.rhezarijaya.githubone.databinding.ActivityMainBinding
import com.rhezarijaya.githubone.ui.activity.detail.DetailActivity
import com.rhezarijaya.githubone.ui.activity.favorite.FavoriteActivity
import com.rhezarijaya.githubone.ui.activity.settings.SettingsActivity
import com.rhezarijaya.githubone.ui.adapter.UserDetailListAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userDetailListAdapter: UserDetailListAdapter

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDetailListAdapter = UserDetailListAdapter {
            startActivity(Intent(this, DetailActivity::class.java).apply {
                putExtra(DetailActivity.USER_DETAIL_KEY, it)
            })
        }

        binding.apply {
            rvUser.adapter = userDetailListAdapter
            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)

            searchView.setupWithSearchBar(searchBar)

            searchBar.inflateMenu(R.menu.menu_main)
            searchBar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_setting -> {
                        startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
                    }

                    R.id.menu_favorite -> {
                        startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
                    }
                }

                true
            }

            searchView.editText.setOnEditorActionListener { _, _, _ ->
                val query = searchView.text.toString()

                mainViewModel.searchUser(query)
                searchBar.text = query
                searchView.hide()

                false
            }

            mainViewModel.isLoading.observe(this@MainActivity) { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

                if (isLoading) {
                    tvNoData.visibility = View.GONE
                }
            }

            mainViewModel.searchResult.observe(this@MainActivity) {
                it?.let { searchResponse ->
                    userDetailListAdapter.submitList(searchResponse.items)
                    tvNoData.visibility =
                        if (searchResponse.items.isEmpty()) View.VISIBLE else View.GONE
                } ?: run {
                    userDetailListAdapter.submitList(ArrayList<UserDetailResponse>())
                }
            }

            mainViewModel.errorMessage.observe(this@MainActivity) {
                it.getData()?.let { message ->
                    Snackbar.make(root, message, Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}