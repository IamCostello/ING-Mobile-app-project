package com.example.kotlinpostapi.network

import com.example.kotlinpostapi.apiObjects.Post
import com.example.kotlinpostapi.apiObjects.User
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApiService {

    @GET("/posts")
    fun posts(): Deferred<List<Post>>

    @GET("users/{userId}")
    suspend fun getUserData(@Path("userId") id: Int): User
}