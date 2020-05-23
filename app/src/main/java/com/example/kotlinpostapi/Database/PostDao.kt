package com.example.kotlinpostapi.Database

import androidx.room.*
import androidx.paging.DataSource
import com.example.kotlinpostapi.apiObjects.Post

@Dao
interface PostDao{
    @Insert
    suspend fun insertPost(post: Post)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(posts: List<Post>?)

    @Delete
    suspend fun deletePost(post: Post)

    @Query("DELETE FROM Post WHERE id IS not null")
    suspend fun clearCache()

    @Query("DELETE FROM Post")
    suspend fun clearDB()

    @Query("SELECT * FROM Post")
    suspend fun getAllPosts(): List<Post>

    @Query("SELECT * FROM Post ORDER BY id")
    fun getPostsData(): DataSource.Factory<Int, Post>

    @Query("SELECT MAX(id) FROM Post where id IS NOT null")
    suspend fun getNextIndex(): Int?
}