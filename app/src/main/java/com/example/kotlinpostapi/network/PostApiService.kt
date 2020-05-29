package com.example.kotlinpostapi.network

import com.example.kotlinpostapi.apiObjects.*
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostApiService {

    @GET("/posts")
    fun posts(): Deferred<List<Post>>

    @GET("/users/{userId}")
    fun getUserData(@Path("userId") id: Int): Deferred<User>

    @GET("/users")
    fun getUserList(): Deferred<List<User>>

    @GET("/comments")
    fun comments(): Deferred<List<Comment>>

    @GET("/albums")
    fun albums(): Deferred<List<Album>>

    @GET("/users/{userId}/albums")
    fun getAlbumData(@Path("albumId") id: Int): Deferred<Album>

    @GET("/posts")
    fun getNextPosts(@Query("_start") start: Int, @Query("_limit") limit: Int): Deferred<List<Post>>

    @GET("/posts")
    fun getNextPostsCall(@Query("_start") start: Int, @Query("_limit") limit: Int): Call<List<Post>>

    @GET("/photos")
    fun photos() : Deferred<List<Photo>>

    @GET("/albums/{albumId}/photos")
    fun getPhotoData(@Path("albumId") id: Int): Deferred<Photo>


}