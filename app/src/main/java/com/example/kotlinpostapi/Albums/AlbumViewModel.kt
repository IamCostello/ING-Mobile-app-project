package com.example.kotlinpostapi.Albums

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinpostapi.apiObjects.Album
import com.example.kotlinpostapi.apiObjects.User
import com.example.kotlinpostapi.repository.AlbumRepository
import com.example.kotlinpostapi.repository.UserRepository
import com.example.kotlinpostapi.util.Result
import com.example.kotlinpostapi.util.ResultType
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.logging.Logger

class AlbumViewModel(
    private val albumRepository: AlbumRepository,
    private val userRepository: UserRepository
) : ViewModel() {


    val albumLiveData: MutableLiveData<List<Album>> = MutableLiveData()
    val userLiveData: MutableLiveData<User> = MutableLiveData()
    val isErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getAlbums(user: User) {
        Timber.d("getAlbums")
        viewModelScope.launch {
            val apiResult = albumRepository.getAlbums()
            val uiResult =
                Result(apiResult.resultType, apiResult.data?.filter { it.userId == user.id })
            updateAlbumsLiveViewData(uiResult)
        }
    }

    fun getUser(userId: Int) {

        viewModelScope.launch {
            val apiResult = userRepository.getUserList()
            val uiResult =
                Result(apiResult.resultType, apiResult.data?.filter { it.id == userId }?.first())
            updateUserLiveData(uiResult)
        }
    }

    fun updateUserLiveData(result: Result<User>) {
        if (result.resultType == ResultType.SUCCESS) {
            userLiveData.postValue(result.data)
            Logger.getAnonymousLogger().info("updateCommentsLiveViewData SUCCESS")
        } else {
            onError()
        }
    }

    private fun updateAlbumsLiveViewData(result: Result<List<Album>>) {
        if (result.resultType == ResultType.SUCCESS) {
            albumLiveData.postValue(result.data)
            Logger.getAnonymousLogger().info("updatePostsLiveViewData SUCCESS")
        } else {
            onError()
        }
    }

    private fun onError() = isErrorLiveData.postValue(true)
}