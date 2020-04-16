package com.example.kotlinpostapi.apiObjects

class Post {
    var userId: Int? = null

    var id: Int? = null

    var title: String? = null

    var body: String? = null

    override fun toString(): String {
        return "User ID: $userId, id: $id, title: $title, body: $body"
    }
}