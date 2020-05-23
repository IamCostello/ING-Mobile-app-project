package com.example.kotlinpostapi.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun PagingRequestHelper.createStatusLiveData(): LiveData<NetworkState> {
    val liveData = MutableLiveData<NetworkState>()
    addListener { report ->
        when {
            report.hasRunning() -> liveData.postValue(NetworkState.LOADING)
            report.hasError() -> liveData.postValue(
                NetworkState.error("Failed loading data"))
            else -> liveData.postValue(NetworkState.LOADED)
        }
    }
    return liveData
}