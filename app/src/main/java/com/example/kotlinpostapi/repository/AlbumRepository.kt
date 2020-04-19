package com.example.kotlinpostapi.repository

import com.example.kotlinpostapi.apiObjects.Album
import com.example.kotlinpostapi.network.PostApiService
import com.example.kotlinpostapi.util.Result


class AlbumRepository(private val albumApiService : PostApiService) : BaseRepository() {

    suspend fun getAlbums(): Result<List<Album>> = handleApiCall(
        call = { albumApiService.albums().await() },
        errMessage = "Failed loading posts"
    )
}