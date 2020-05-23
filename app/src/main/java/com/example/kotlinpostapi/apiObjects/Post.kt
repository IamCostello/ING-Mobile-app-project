package com.example.kotlinpostapi.apiObjects

import androidx.room.Entity
import androidx.room.PrimaryKey

//class Post {
//    var userId: Int? = null
//
//    var id: Int? = null
//
//    var title: String? = null
//
//    var body: String? = null
//
//    var username: String? = null
//
//    var commentsCount: Int? = null
//
//    override fun toString(): String {
//        return "User ID: $userId, id: $id, title: $title, body: $body"
//    }
//}

@Entity
data class Post(
    @PrimaryKey(autoGenerate = true)
    val postId: Int?,
    val userId: Int?,
    val id: Int?,
    var title: String?,
    var body: String?,
    var username: String?,
    var commentsCount: Int?
)