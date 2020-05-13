package com.example.kotlinpostapi.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class UserPost(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    val username: String?,
    val commentCount: Int?
)