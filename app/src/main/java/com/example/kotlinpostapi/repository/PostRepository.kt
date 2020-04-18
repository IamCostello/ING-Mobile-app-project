package com.example.kotlinpostapi.repository

import com.example.kotlinpostapi.apiObjects.Post
import com.example.kotlinpostapi.network.PostApiService
import com.example.kotlinpostapi.util.Result

class PostRepository(private val postApiService: PostApiService): BaseRepository() {

//    suspend fun getPosts(): Result<List<Post>> {
//        Logger.getAnonymousLogger().info("getPosts: Repository")
//        var result: Result<List<Post>> = Result.success(null)
//        withContext(Dispatchers.IO) {
//            try {
//                val request = postApiService.posts();
//
//                val response: List<Post> = request.await()
//                Logger.getAnonymousLogger().info("onPostRecived ${response}")
//                request.let {
//                    if (it.isCompleted) {
//                        result = Result.success(response)
//                        Logger.getAnonymousLogger().info("isCompleted")
//                    } else if (it.isCancelled) {
//                        result = Result.error(CancelledFetchException())
//                    }
//                }
//            } catch (exception: Throwable) {
//                result = Result.error(NetworkException())
//                Logger.getAnonymousLogger().info("Network exception")
//            }
//        }
//        return result
//    }

    suspend fun getPosts(): Result<List<Post>> = handleApiCall (
            call = {postApiService.posts().await()},
            errMessage = "Failed loading posts"
        )
}

//class CancelledFetchException : Exception()
//class NetworkException : Exception()