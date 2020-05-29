package com.example.kotlinpostapi.util

import androidx.paging.PagedList
import com.example.kotlinpostapi.apiObjects.Post
import com.example.kotlinpostapi.network.PostApiService
import com.example.kotlinpostapi.repository.PostRepository
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.coroutineContext

class PostBoundaryCallback (
    private val postRepository: PostRepository,
    private val postApiService: PostApiService,
    private val pageSize: Int
) : PagedList.BoundaryCallback<Post>() {

    val helper = PagingRequestHelper(Dispatchers.IO.asExecutor())
    val networkState = helper.createStatusLiveData()

    override fun onZeroItemsLoaded() {
//        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
//            postApiService.getNextPostsCall(0, pageSize).enqueue(postApiCallback(it))
//            println("Get next post for 0")
//        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: Post) {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            CoroutineScope(Dispatchers.IO).launch {
                    val start = postRepository.getNextIndex()
                    postApiService.getNextPostsCall(start, pageSize).enqueue(postApiCallback(it))
                    println("Get next post for $start")
            }
        }
    }

    override fun onItemAtFrontLoaded(itemAtFront: Post) {
        //
    }

    private fun insertIntoDb(response: Response<List<Post>>, it: PagingRequestHelper.Request.Callback) {
        CoroutineScope(Dispatchers.IO).launch {
            postRepository.insertResultIntoDb(response.body() ?: listOf())
            it.recordSuccess()
        }
    }

    private fun postApiCallback(it: PagingRequestHelper.Request.Callback) : Callback<List<Post>> {
        return object : Callback<List<Post>> {
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                it.recordFailure(t)
            }

            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                insertIntoDb(response, it)
            }
        }
    }
}