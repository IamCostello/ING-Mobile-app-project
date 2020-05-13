package com.example.kotlinpostapi.Database

import androidx.room.*

@Dao
interface PostDao{
    @Insert
    suspend fun createPost(userPost: UserPost)

    @Delete
    suspend fun deletePost(userPost: UserPost)
    //suspend fun deletePost(postId: Int)

    @Query("SELECT * FROM UserPost")
    suspend fun getAllPosts(): List<UserPost>

    //@Update
}