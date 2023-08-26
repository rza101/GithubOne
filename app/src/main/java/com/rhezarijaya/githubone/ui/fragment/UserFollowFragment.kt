package com.rhezarijaya.githubone.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rhezarijaya.githubone.data.network.response.UserDetailResponse
import com.rhezarijaya.githubone.databinding.FragmentUserFollowBinding
import com.rhezarijaya.githubone.ui.activity.detail.DetailActivity
import com.rhezarijaya.githubone.ui.activity.detail.DetailViewModel
import com.rhezarijaya.githubone.ui.adapter.UserDetailListAdapter

class UserFollowFragment : Fragment() {
    companion object {
        const val FRAGMENT_TYPE = "FRAGMENT_TYPE"
        const val TYPE_FOLLOWERS = "TYPE_FOLLOWERS"
        const val TYPE_FOLLOWING = "TYPE_FOLLOWING"
    }

    private lateinit var binding: FragmentUserFollowBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var userDetailListAdapter: UserDetailListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel = ViewModelProvider(requireActivity())[DetailViewModel::class.java]
        userDetailListAdapter = UserDetailListAdapter {
            startActivity(Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra(DetailActivity.USER_DETAIL_KEY, it)
            })
        }

        binding.apply {
            rvUser.apply {
                adapter = userDetailListAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }

            when (arguments?.getString(FRAGMENT_TYPE)) {
                TYPE_FOLLOWERS -> {
                    detailViewModel.userFollowers.observe(viewLifecycleOwner) {
                        it?.let {
                            userDetailListAdapter.submitList(it)
                            tvNoData.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
                        } ?: run {
                            userDetailListAdapter.submitList(ArrayList<UserDetailResponse>())
                        }
                    }

                    detailViewModel.isUserFollowersLoading.observe(viewLifecycleOwner) { isLoading ->
                        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

                        if (isLoading) {
                            tvNoData.visibility = View.GONE
                        }
                    }

                    detailViewModel.userFollowersErrorMessage.observe(viewLifecycleOwner) {
                        it.getData()?.let { message ->
                            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                        }
                    }
                }

                TYPE_FOLLOWING -> {
                    detailViewModel.userFollowing.observe(viewLifecycleOwner) {
                        it?.let {
                            userDetailListAdapter.submitList(it)
                            tvNoData.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
                        } ?: run {
                            userDetailListAdapter.submitList(ArrayList<UserDetailResponse>())
                        }
                    }

                    detailViewModel.isUserFollowingLoading.observe(viewLifecycleOwner) { isLoading ->
                        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

                        if (isLoading) {
                            tvNoData.visibility = View.GONE
                        }
                    }

                    detailViewModel.userFollowingErrorMessage.observe(viewLifecycleOwner) {
                        it.getData()?.let { message ->
                            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}