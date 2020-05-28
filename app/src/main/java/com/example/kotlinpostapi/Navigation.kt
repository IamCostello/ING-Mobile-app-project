package com.example.kotlinpostapi

import android.renderscript.Double2
import com.example.kotlinpostapi.apiObjects.Geo
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
        fun onMapClick(userLat:String?, userLng:String?)
    }

    interface OnLogInClickListener{
        fun onLogInClick()
    }

    interface OnRegisterClickListener{
        fun onRegisterClick()
    }

    interface OnExistingUserClickListener{
        fun onExistingClick()
    }

    interface OnMoveToRegisterClickListener{
        fun onMoveToRegisterClick()
    }


}