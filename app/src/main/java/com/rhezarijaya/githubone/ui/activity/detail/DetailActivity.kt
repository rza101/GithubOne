package com.rhezarijaya.githubone.ui.activity.detail

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.rhezarijaya.githubone.R
import com.rhezarijaya.githubone.data.network.response.UserDetailResponse
import com.rhezarijaya.githubone.data.room.entity.Favorite
import com.rhezarijaya.githubone.databinding.ActivityDetailBinding
import com.rhezarijaya.githubone.ui.adapter.UserFollowPagerAdapter
import com.rhezarijaya.githubone.utils.Result
import com.rhezarijaya.githubone.utils.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var userFollowPagerAdapter: UserFollowPagerAdapter

    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var userDetailData: UserDetailResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userDetailData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(USER_DETAIL_KEY, UserDetailResponse::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(USER_DETAIL_KEY)
        }

        userFollowPagerAdapter = UserFollowPagerAdapter(this)

        binding.apply {
            viewPager.adapter = userFollowPagerAdapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = UserFollowPagerAdapter.TAB_TITLES[position]
            }.attach()

            ibBack.setOnClickListener {
                finish()
            }
        }

        userDetailData?.let { userDetailData ->
            detailViewModel.getUserDetail(userDetailData.login ?: "").observe(this) {
                when (it) {
                    is Result.Success -> {
                        setLoadingVisibility(false)

                        val userDetail = it.data
                        binding.apply {
                            Glide.with(this@DetailActivity)
                                .load(userDetail.avatarUrl)
                                .placeholder(R.drawable.baseline_person_24)
                                .error(R.drawable.baseline_person_24)
                                .into(civAvatar)

                            Glide.with(this@DetailActivity)
                                .load(
                                    if (userDetail.isOnFavorite) {
                                        R.drawable.baseline_favorite_24
                                    } else {
                                        R.drawable.baseline_favorite_border_24
                                    }
                                )
                                .into(fabFavorite)

                            fabFavorite.setOnClickListener {
                                val favorite = Favorite(
                                    userDetail.login!!,
                                    userDetail.id!!,
                                    userDetail.avatarUrl
                                )

                                if (!userDetail.isOnFavorite) {
                                    detailViewModel.setFavorite(favorite)
                                } else {
                                    detailViewModel.removeFavorite(favorite)
                                }
                            }

                            tvFullname.text = userDetail.name ?: "-"
                            tvUsername.text =
                                resources.getString(
                                    R.string.username_template,
                                    userDetail.login
                                )
                            tvUserid.text =
                                resources.getString(
                                    R.string.detail_userid_template,
                                    userDetail.id.toString()
                                )
                            tvEmail.text =
                                resources.getString(
                                    R.string.detail_email_template,
                                    userDetail.email ?: "-"
                                )
                            tvRepoCount.text = resources.getString(
                                R.string.detail_repo_count_template,
                                (userDetail.publicRepos ?: 0).toString()
                            )
                            tvFollowers.text = resources.getString(
                                R.string.detail_followers_count_template,
                                (userDetail.followers ?: 0).toString()
                            )
                            tvFollowing.text = resources.getString(
                                R.string.detail_following_count_template,
                                (userDetail.following ?: 0).toString()
                            )
                        }
                    }

                    is Result.Loading -> {
                        setLoadingVisibility(true)
                    }

                    is Result.Error -> {
                        setLoadingVisibility(false)
                        it.errorData.getData()?.let { message ->
                            Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
            }

            detailViewModel.loadUserFollowers(userDetailData.login ?: "")
            detailViewModel.loadUserFollowing(userDetailData.login ?: "")
        } ?: run {
            finish()
        }
    }

    private fun setLoadingVisibility(isLoading: Boolean) {
        val visibility = if (isLoading) View.INVISIBLE else View.VISIBLE

        binding.apply {
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            civAvatar.visibility = visibility
            tvFullname.visibility = visibility
            tvUsername.visibility = visibility
            tvUserid.visibility = visibility
            tvEmail.visibility = visibility
            tvRepoCount.visibility = visibility
            tvFollowers.visibility = visibility
            tvFollowing.visibility = visibility
            fabFavorite.visibility = visibility
        }
    }

    companion object {
        const val USER_DETAIL_KEY = "USER_DETAIL_KEY"
    }
}