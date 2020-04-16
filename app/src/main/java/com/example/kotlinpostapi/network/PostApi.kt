package com.example.kotlinpostapi.network

import android.content.Context
import android.util.Log
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.logging.Logger

class PostApi(private val context: Context) {

    val SERVERR_URL ="https://jsonplaceholder.typicode.com"

    fun getPostApiService() : PostApiService {

        val retrofit= Retrofit.Builder()
            .baseUrl(SERVERR_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        Logger.getAnonymousLogger().info("getPostApiService")
        return retrofit.create(PostApiService::class.java)
    }
}