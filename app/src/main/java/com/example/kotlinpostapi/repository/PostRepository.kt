package com.example.kotlinpostapi.repository

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.toLiveData
import com.example.kotlinpostapi.Database.PostDao
import com.example.kotlinpostapi.apiObjects.Post
import com.example.kotlinpostapi.network.PostApiService
import com.example.kotlinpostapi.util.Listing
import com.example.kotlinpostapi.util.PostBoundaryCallback
import com.example.kotlinpostapi.util.Result
import androidx.lifecycle.switchMap
import com.example.kotlinpostapi.util.NetworkState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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

    suspend fun insertResultIntoDb(posts: List<Post>) {
        postDao.insertPost(posts)
    }

    suspend fun getNextIndex(): Int = postDao.getNextIndex() ?: 0

    @MainThread
    private fun refresh(): LiveData<NetworkState> {
        val networkState = MutableLiveData<NetworkState>()

        GlobalScope.launch {
            networkState.postValue(NetworkState.LOADING)
            try {
                val response = postApiService.getNextPosts(0, DEFAULT_PAGE_SIZE).await()
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