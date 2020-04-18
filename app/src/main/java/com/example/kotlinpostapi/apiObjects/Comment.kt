package com.example.kotlinpostapi.apiObjects

class Comment {
    var postId: Int? = null

    var id: Int? = null

    var name: String? = null

    var email: String? = null

    var body: String? = null

    override fun toString(): String{
        return "PostId: $postId, Id: $id, Name: $name, Email: $email, Body: $body"
    }
}