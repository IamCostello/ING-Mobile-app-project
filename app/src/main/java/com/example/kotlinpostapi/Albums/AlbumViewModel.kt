package com.example.kotlinpostapi.Albums

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinpostapi.apiObjects.Album
import com.example.kotlinpostapi.repository.AlbumRepository
import com.example.kotlinpostapi.util.ResultType
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.logging.Logger

class AlbumViewModel(private val albmRepository: AlbumRepository) : ViewModel() {


    val albumLiveData: MutableLiveData<List<Album>> = MutableLiveData()
    val isErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getAlbums() {
        Timber.d("getAlbums")
        viewModelScope.launch {
            val apiResult = albmRepository.getAlbums()
            updateAlbumsLiveViewData(apiResult)
        }
    }

    private fun updateAlbumsLiveViewData(result: com.example.kotlinpostapi.util.Result<List<Album>>) {
        if (result.resultType == ResultType.SUCCESS) {
            albumLiveData.postValue(result.data)
            Logger.getAnonymousLogger().info("updatePostsLiveViewData SUCCESS")
        } else {
            onError()
        }
    }

    private fun onError() = isErrorLiveData.postValue(true)
}