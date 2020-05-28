package com.example.kotlinpostapi.repository

import com.example.kotlinpostapi.apiObjects.User
import com.example.kotlinpostapi.network.PostApiService
import com.example.kotlinpostapi.util.Result

class UserRepository(private val postApiService: PostApiService): BaseRepository() {
    suspend fun getUserData(id: Int): Result<User> = handleApiCall (
        call = {postApiService.getUserData(id).await()},
        errMessage = "Failed loading user data"
    )

    suspend fun getUserList(): Result<List<User>> = handleApiCall(
        call = {postApiService.getUserList().await()},
        errMessage = "Failed loading users list"
    )
}