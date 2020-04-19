package com.example.kotlinpostapi

import com.example.kotlinpostapi.apiObjects.Post
import com.example.kotlinpostapi.apiObjects.User

class Navigation {
    interface OnUserClickListener{
        fun onUserClick(userId: Int?)
    }

    interface OnPostClickListener{
        fun onPostClick(post: Post)
    }

    interface OnAlbumClickListener{
        fun onAlbumClick(userId: Int?)
    }
}