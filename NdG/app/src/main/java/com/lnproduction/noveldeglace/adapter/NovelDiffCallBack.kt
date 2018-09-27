package com.lnproduction.noveldeglace.adapter

import androidx.recyclerview.widget.DiffUtil
import com.lnproduction.noveldeglace.model.Novel

class NovelDiffCallBack : DiffUtil.ItemCallback<Novel>(){
    override fun areItemsTheSame(oldItem: Novel, newItem: Novel): Boolean {
        return oldItem?.novelId == newItem?.novelId
    }

    override fun areContentsTheSame(oldItem: Novel, newItem: Novel): Boolean {
        return oldItem == newItem
    }
}