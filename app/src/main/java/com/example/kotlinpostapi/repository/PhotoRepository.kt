package com.example.kotlinpostapi.repository

import com.example.kotlinpostapi.apiObjects.Photo
import com.example.kotlinpostapi.network.PostApiService
import com.example.kotlinpostapi.util.Result

class PhotoRepository(private val photoApiService: PostApiService): BaseRepository() {

    suspend fun getPhotos(albumId: Int) : Result<Photo> = handleApiCall(
        call = {photoApiService.getPhotoData(albumId).await()},
        errMessage = "Failed loading photos"
    )

    suspend fun getPhotos(): Result<List<Photo>> = handleApiCall (
        call = {photoApiService.photos().await()},
        errMessage = "Failed loading photos"
    )
}