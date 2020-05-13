package com.example.kotlinpostapi.repository

import com.example.kotlinpostapi.Database.PostDao
import com.example.kotlinpostapi.Database.UserPost
import com.example.kotlinpostapi.apiObjects.Post
import com.example.kotlinpostapi.network.PostApiService
import com.example.kotlinpostapi.util.Result

class PostRepository(private val postDao: PostDao, private val postApiService: PostApiService): BaseRepository() {
    suspend fun getPosts(): Result<List<Post>> = handleApiCall (
            call = {postApiService.posts().await()},
            errMessage = "Failed loading posts"
        )

    suspend fun getUserPosts(): Result<List<UserPost>> = handleApiCall (
        call = {postDao.getAllPosts()},
        errMessage = "Failed loading user posts"
    )

    suspend fun insert(post: UserPost) {
        postDao.createPost(post)
    }
}