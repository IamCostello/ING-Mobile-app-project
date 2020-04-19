package com.example.kotlinpostapi.apiObjects

class Album {
    var userId: Int? = null
    var id: Int? = null
    var title: String? = null

    override fun toString(): String {
        return "User ID: $userId, id: $id, title: $title"
    }
}