package com.example.kotlinpostapi.Comments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinpostapi.apiObjects.Comment
import com.example.kotlinpostapi.apiObjects.Post
import com.example.kotlinpostapi.repository.CommentsRepository
import com.example.kotlinpostapi.util.ResultType
import com.example.kotlinpostapi.util.Result
import kotlinx.coroutines.launch
import java.util.logging.Logger

class CommentsListViewModel(private val commentsRepository: CommentsRepository) : ViewModel() {
    val commentsLiveData: MutableLiveData<List<Comment>> = MutableLiveData()
    val isErrorLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getComments(post: Post){
        viewModelScope.launch{
            val apiResult = commentsRepository.getComments()
            val uiResult = Result(apiResult.resultType, apiResult.data?.filter{it.postId == post.id})

        }
    }

    fun updateCommentsLiveData(result: Result<List<Comment>>){
        if (result.resultType == ResultType.SUCCESS){
            commentsLiveData.postValue(result.data)
            Logger.getAnonymousLogger().info("updateCommentsLiveViewData SUCCESS")
        }else{
            onError()
        }
    }

    private fun onError() = isErrorLiveData.postValue(true)
}
