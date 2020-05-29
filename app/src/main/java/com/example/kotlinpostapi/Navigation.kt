package com.example.kotlinpostapi

import com.example.kotlinpostapi.apiObjects.Post

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

    interface  OnPhotoClickListener{
        fun onPhotoClick(albumId: Int?)
    }

    interface OnMapClickListener{
        fun onMapClick(userLat:String?, userLng:String?, street: String?, city: String?, suite: String?)
    }
}