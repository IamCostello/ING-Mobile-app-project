package com.example.kotlinpostapi.repository

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.toLiveData
import com.example.kotlinpostapi.Database.PostDao
import com.example.kotlinpostapi.apiObjects.Post
import com.example.kotlinpostapi.network.PostApiService
import androidx.lifecycle.switchMap
import com.example.kotlinpostapi.util.*
import kotlinx.coroutines.*
import java.lang.Exception

class PostRepository(private val postDao: PostDao, private val postApiService: PostApiService): BaseRepository() {
    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }

    suspend fun getPosts(): Result<List<Post>> = handleApiCall (
            call = {postApiService.posts().await()},
            errMessage = "Failed loading posts"
        )

    suspend fun getUserPosts(): Result<List<Post>> = handleApiCall (
        call = {postDao.getAllPosts()},
        errMessage = "Failed loading user posts"
    )

    fun createPost(post: Post) {
        GlobalScope.launch {
            postDao.insertPost(post)
        }
    }

    fun insertResultIntoDb(posts: List<Post>) {
        postDao.insertPost(posts)
    }

    suspend fun getNextIndex(): Int = postDao.getNextIndex() ?: 0

    suspend fun getUserNamesList(): List<String> {
        try{
            return UiParser.getUsernames(handleApiCall(
                call = {postApiService.getUserList().await()},
                errMessage = "Failed loading usernames list"
            ))
        }
        catch(exception: Exception) {
            return listOf()
        }
    }

    suspend fun getCommentCount(postList: List<Post>?): List<Int> {
        try{
            return UiParser.getCommentsCount(postList, handleApiCall(
                call =  {postApiService.comments().await()},
                errMessage = "Failed loading comments count list"
            ))
        }
        catch (exception: Exception){
            exception.printStackTrace()
            return listOf()
        }
    }

    @MainThread
    private fun refresh(): LiveData<NetworkState> {
        val networkState = MutableLiveData<NetworkState>()

        CoroutineScope(Dispatchers.IO).async {
            networkState.postValue(NetworkState.LOADING)
            try {
                val response = postApiService.getNextPosts(0, DEFAULT_PAGE_SIZE).await()
                val userList = getUserNamesList()
                //val commentCount = getCommentCount(response)
                response.map { it.username = userList[it.userId!!-1] }
                //response.map { it.commentsCount = commentCount[it.id!!-1] }
                postDao.clearCache()
                postDao.insertPost(response)
                networkState.postValue(NetworkState.LOADED)
            }
            catch(exception: Exception) {
                exception.printStackTrace()
                networkState.postValue(NetworkState.error("Failed loading top posts"))
            }
        }

        return networkState
    }

    @MainThread
    fun getPostFactory(): Listing<Post> {
        val boundaryCallback = PostBoundaryCallback(this, postApiService, DEFAULT_PAGE_SIZE)
        val refreshTrigger = MutableLiveData(Unit)
        val refreshState: LiveData<NetworkState> = refreshTrigger.switchMap { refresh() }

        val pagedList = postDao.getPostsData().toLiveData(
            pageSize = DEFAULT_PAGE_SIZE,
            boundaryCallback = boundaryCallback)

        return Listing(
            pagedList = pagedList,
            networkState = boundaryCallback.networkState,
            refreshState = refreshState,
            refresh = {
                refreshTrigger.value = Unit
            },
            retry = {
                boundaryCallback.helper.retryAllFailed()
            }
        )
    }
}