package com.example.kotlinpostapi.repository

import com.example.kotlinpostapi.apiObjects.User
import com.example.kotlinpostapi.network.PostApiService
import com.example.kotlinpostapi.util.Result
import com.example.kotlinpostapi.util.ResultType
import com.example.kotlinpostapi.util.UsernameParser
import java.lang.Exception

class UserRepository(private val postApiService: PostApiService): BaseRepository() {
    suspend fun getUserData(id: Int): Result<User> = handleApiCall (
        call = {postApiService.getUserData(id).await()},
        errMessage = "Failed loading user data"
    )

    suspend fun getUserList(): Result<List<String>> {
        try{
            return UsernameParser.getUsernames(handleApiCall(
                call = {postApiService.getUserList().await()},
                errMessage = "Failed loading users list"
            ))
        }
        catch(exception: Exception) {
            exception.printStackTrace()
            return Result(ResultType.ERROR, null)
        }
    }
}