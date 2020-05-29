package com.example.kotlinpostapi.network

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import java.util.logging.Logger


class PostApi(private val context: Context) {

    val SERVERR_URL ="https://jsonplaceholder.typicode.com"

    fun getPostApiService() : PostApiService {

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit= Retrofit.Builder()
            .baseUrl(SERVERR_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()

        Logger.getAnonymousLogger().info("getPostApiService")
        return retrofit.create(PostApiService::class.java)
    }
}