package com.example.kotlinpostapi.photos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinpostapi.apiObjects.Album
import com.example.kotlinpostapi.apiObjects.Photo
import com.example.kotlinpostapi.repository.AlbumRepository
import com.example.kotlinpostapi.repository.PhotoRepository
import kotlinx.coroutines.launch
import com.example.kotlinpostapi.util.Result
import com.example.kotlinpostapi.util.ResultType
import java.util.logging.Logger

class PhotoListViewModel (private val photosRepository: PhotoRepository, private val albumRepository: AlbumRepository) : ViewModel(){

    val photosLiveData: MutableLiveData<List<Photo>> = MutableLiveData()
    val albumLiveData: MutableLiveData<Album> = MutableLiveData()
    val isErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getPhotos(album: Album){
        viewModelScope.launch {
            val apiResult = photosRepository.getPhotos()
            val uiResult = Result(apiResult.resultType,apiResult.data?.filter { it.albumId == album.id })
            updatePhotosLiveData(uiResult)
        }
    }

    fun getAlbum(albumId: Int){
        viewModelScope.launch {
            val apiResult = albumRepository.getAlbums()
            val uiResult = Result(apiResult.resultType, apiResult.data?.filter { it.id == albumId }?.first())
            updateAlbumsLiveData(uiResult)
        }
    }



    fun updatePhotosLiveData(result: Result<List<Photo>>){
        if(result.resultType == ResultType.SUCCESS){
            photosLiveData.postValue(result.data)
            Logger.getAnonymousLogger().info("Succes update photos")

        }else
            onError()
    }


    fun updateAlbumsLiveData(result: Result<Album>){
        if(result.resultType == ResultType.SUCCESS){
            albumLiveData.postValue(result.data)
            Logger.getAnonymousLogger().info("Succes update album")
        } else
            onError()
    }


    private fun onError() = isErrorLiveData.postValue(true)

}