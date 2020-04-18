package com.example.kotlinpostapi.repository

import com.example.kotlinpostapi.apiObjects.Comment
import com.example.kotlinpostapi.network.PostApiService
import com.example.kotlinpostapi.util.Result


class CommentsRepository(private val postApiService: PostApiService): BaseRepository() {
    suspend fun getComments(): Result<List<Comment>> = handleApiCall (
        call = {postApiService.comments().await()},
        errMessage = "Failed loading comments"
    )
}