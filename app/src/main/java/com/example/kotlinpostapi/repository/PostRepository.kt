package com.example.kotlinpostapi.repository

import com.example.kotlinpostapi.apiObjects.Post
import com.example.kotlinpostapi.network.PostApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.Exception
import com.example.kotlinpostapi.Result
import java.util.logging.Logger

class PostRepository(private val postApiService: PostApiService) {

    suspend fun getPosts(): Result<List<Post>> {
        Logger.getAnonymousLogger().info("getPosts: Repository")
        var result: Result<List<Post>> = Result.success(listOf())
        withContext(Dispatchers.IO) {
            try {
                val request = postApiService.posts();

                val response: List<Post> = request.await()
                Logger.getAnonymousLogger().info("onPostRecived ${response}")
                request.let {
                    if (it.isCompleted) {
                        result = Result.success(response)
                        Logger.getAnonymousLogger().info("isCompleted")
                    } else if (it.isCancelled) {
                        result = Result.error(CancelledFetchException())
                    }
                }
            } catch (exception: Throwable) {
                result = Result.error(NetworkException())
                Logger.getAnonymousLogger().info("Network exception")
            }
        }
        return result
    }
}

class CancelledFetchException : Exception()
class NetworkException : Exception()