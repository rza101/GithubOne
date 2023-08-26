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
import com.rhezarijaya.githubone.databinding.ActivityDetailBinding
import com.rhezarijaya.githubone.ui.adapter.UserFollowPagerAdapter

class DetailActivity : AppCompatActivity() {
    // TODO companion object at bottom
    companion object {
        const val USER_DETAIL_KEY = "USER_DETAIL_KEY"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var userFollowPagerAdapter: UserFollowPagerAdapter

    private val detailViewModel by viewModels<DetailViewModel>()
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
        }

        userDetailData?.let { userDetailData ->
            detailViewModel.loadUserDetail(userDetailData.login ?: "")
            detailViewModel.loadUserFollowers(userDetailData.login ?: "")
            detailViewModel.loadUserFollowing(userDetailData.login ?: "")

            detailViewModel.userDetail.observe(this) { userDetail ->
                if (userDetail != null) {
                    binding.apply {
                        Glide.with(this@DetailActivity)
                            .load(userDetail.avatarUrl)
                            .placeholder(R.drawable.baseline_person_24)
                            .error(R.drawable.baseline_person_24)
                            .into(civAvatar)

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
            }

            detailViewModel.isUserDetailLoading.observe(this) { isLoading ->
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
                }
            }

            detailViewModel.userDetailErrorMessage.observe(this) {
                it.getData()?.let { message ->
                    Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
                }
            }
        } ?: run {
            finish()
        }
    }
}