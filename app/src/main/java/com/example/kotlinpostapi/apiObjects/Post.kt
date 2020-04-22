package com.example.kotlinpostapi.apiObjects

class Post {
    var userId: Int? = null

    var id: Int? = null

    var title: String? = null

    var body: String? = null

    var username: String? = null

    var commentsCount: Int? = null

    override fun toString(): String {
        return "User ID: $userId, id: $id, title: $title, body: $body"
    }
}