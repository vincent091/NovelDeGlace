package com.lnproduction.noveldeglace.adapter

import androidx.recyclerview.widget.DiffUtil
import com.lnproduction.noveldeglace.model.Post

class PostDiffCallBack : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem?.novelId == newItem?.novelId
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}