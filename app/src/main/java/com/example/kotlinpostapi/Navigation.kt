package com.example.kotlinpostapi

import com.example.kotlinpostapi.apiObjects.Post

class Navigation {
    interface OnUserClickListener{
        fun onUserClick(userId: Int?)
    }

    interface OnPostClickListener{
        fun onPostClick(post: Post)
    }
}