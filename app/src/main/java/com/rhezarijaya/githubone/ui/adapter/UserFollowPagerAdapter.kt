package com.rhezarijaya.githubone.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rhezarijaya.githubone.ui.fragment.UserFollowFragment

class UserFollowPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    companion object {
        val TAB_TITLES = listOf("Followers", "Followings")
    }

    override fun getItemCount() = TAB_TITLES.size

    override fun createFragment(position: Int) = when (position) {
        0 -> {
            UserFollowFragment().apply {
                arguments = Bundle().apply {
                    putString(
                        UserFollowFragment.FRAGMENT_TYPE,
                        UserFollowFragment.TYPE_FOLLOWERS
                    )
                }
            }
        }

        1 -> {
            UserFollowFragment().apply {
                arguments = Bundle().apply {
                    putString(
                        UserFollowFragment.FRAGMENT_TYPE,
                        UserFollowFragment.TYPE_FOLLOWING
                    )
                }
            }
        }

        else -> null
    }!!
}