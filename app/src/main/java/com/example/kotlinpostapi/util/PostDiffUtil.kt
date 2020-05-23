package com.example.kotlinpostapi.util

import androidx.recyclerview.widget.DiffUtil
import com.example.kotlinpostapi.apiObjects.Post

object PostDiffUtil : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.postId == newItem.postId
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.postId == newItem.postId && oldItem.body == newItem.body
    }
}