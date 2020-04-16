package com.example.kotlinpostapi.Posts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinpostapi.ResultType
import com.example.kotlinpostapi.apiObjects.Post
import com.example.kotlinpostapi.repository.PostRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.logging.Logger

class PostViewModel(private val postRepository: PostRepository) : ViewModel() {
    val postsLiveData: MutableLiveData<List<Post>> = MutableLiveData()
    val isErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()


    fun getPosts() {
        Timber.d("getPosts")
        viewModelScope.launch {
            val apiResult = postRepository.getPosts()
            updatePostsLiveViewData(apiResult)
        }
    }

    private fun updatePostsLiveViewData(result: com.example.kotlinpostapi.Result<List<Post>>) {
        if (result.resultType == ResultType.SUCCESS) {
            postsLiveData.postValue(result.data)
            Logger.getAnonymousLogger().info("updatePostsLiveViewData SUCCESS")
        } else {
            onError()
        }
    }


    private fun onError() = isErrorLiveData.postValue(true)
}