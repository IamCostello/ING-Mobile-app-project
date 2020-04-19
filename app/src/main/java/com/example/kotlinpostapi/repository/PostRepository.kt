package com.example.kotlinpostapi.repository

import com.example.kotlinpostapi.apiObjects.Post
import com.example.kotlinpostapi.network.PostApiService
import com.example.kotlinpostapi.util.Result

class PostRepository(private val postApiService: PostApiService): BaseRepository() {
    suspend fun getPosts(): Result<List<Post>> = handleApiCall (
            call = {postApiService.posts().await()},
            errMessage = "Failed loading posts"
        )
}