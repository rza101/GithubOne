package com.rhezarijaya.githubone.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rhezarijaya.githubone.R
import com.rhezarijaya.githubone.data.network.response.UserDetailResponse
import com.rhezarijaya.githubone.databinding.ItemUserBinding

class UserDetailListAdapter(private val onCLickCallback: (UserDetailResponse) -> Unit) :
    ListAdapter<UserDetailResponse, UserDetailListAdapter.ViewHolder>(DIFF_UTIL) {
    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<UserDetailResponse>() {
            override fun areItemsTheSame(
                oldItem: UserDetailResponse,
                newItem: UserDetailResponse
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: UserDetailResponse,
                newItem: UserDetailResponse
            ): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        val context = holder.itemView.context
        val userDetail = getItem(position)

        holder.itemView.setOnClickListener {
            onCLickCallback(userDetail)
        }

        binding.apply {
            tvUsername.text =
                context.resources.getString(R.string.username_template, userDetail.login.orEmpty())
            tvUserid.text =
                context.resources.getString(R.string.userid_template, userDetail.id ?: "0")

            Glide.with(context)
                .load(userDetail.avatarUrl)
                .placeholder(R.drawable.baseline_person_24)
                .error(R.drawable.baseline_person_24)
                .into(civAvatar)
        }
    }

    class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)
}