package com.example.kotlinpostapi.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.example.kotlinpostapi.util.Result

open class BaseRepository {
    suspend fun<T: Any> handleApiCall(call: suspend () -> T, errMessage: String): Result<T> {
        return try{
            val response = withContext(Dispatchers.IO) { call.invoke() }
            Result.success(response)
        }
        catch(exception: Exception){
            exception.printStackTrace()
            Result.error(exception, errMessage)
        }
    }
}