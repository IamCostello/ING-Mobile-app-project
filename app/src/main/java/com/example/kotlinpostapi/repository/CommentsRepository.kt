package com.example.kotlinpostapi.repository

import com.example.kotlinpostapi.apiObjects.Comment
import com.example.kotlinpostapi.apiObjects.Post
import com.example.kotlinpostapi.network.PostApiService
import com.example.kotlinpostapi.util.Result
import com.example.kotlinpostapi.util.ResultType
import com.example.kotlinpostapi.util.UiParser
import java.lang.Exception


class CommentsRepository(private val postApiService: PostApiService): BaseRepository() {
    suspend fun getComments(): Result<List<Comment>> = handleApiCall (
        call = {postApiService.comments().await()},
        errMessage = "Failed loading comments"
    )

    suspend fun getCommentCount(postList: List<Post>?): Result<List<Int>> {
        try{
            return UiParser.getCommentsCount(postList, handleApiCall(
                call =  {postApiService.comments().await()},
                errMessage = "Failed loading comments count list"
            ))
        }
        catch (exception: Exception){
            exception.printStackTrace()
            return Result(ResultType.ERROR, null)
        }
    }
}