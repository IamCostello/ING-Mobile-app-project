package com.example.kotlinpostapi.network

import com.example.kotlinpostapi.apiObjects.Post
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface PostApiService {

    @GET("/posts")
    fun posts(): Deferred<List<Post>>
}