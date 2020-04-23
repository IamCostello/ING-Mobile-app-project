package com.example.kotlinpostapi.Posts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinpostapi.util.ResultType
import com.example.kotlinpostapi.apiObjects.Post
import com.example.kotlinpostapi.repository.CommentsRepository
import com.example.kotlinpostapi.repository.PostRepository
import com.example.kotlinpostapi.repository.UserRepository
import com.example.kotlinpostapi.util.Result
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.logging.Logger

class PostViewModel(private val postRepository: PostRepository, private val userRepository: UserRepository, private val commentsRepository: CommentsRepository) : ViewModel() {
    val postsLiveData: MutableLiveData<List<Post>> = MutableLiveData()
    val isErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getPosts() {
        viewModelScope.launch {
            val apiResult = postRepository.getPosts()
            val userList = userRepository.getUserNamesList()
            val commentsCountList = commentsRepository.getCommentCount(apiResult.data)

            apiResult.data?.map { it.username = userList.data!![it.userId!!-1] }
            apiResult.data?.map { it.commentsCount = commentsCountList.data!![it.userId!!-1] }

            updatePostsLiveViewData(apiResult)
        }
    }


    private fun updatePostsLiveViewData(result: Result<List<Post>>) {
        if (result.resultType == ResultType.SUCCESS) {
            postsLiveData.postValue(result.data)
            Logger.getAnonymousLogger().info("updatePostsLiveViewData SUCCESS")
        } else {
            onError()
        }
    }

    private fun onError() = isErrorLiveData.postValue(true)
}