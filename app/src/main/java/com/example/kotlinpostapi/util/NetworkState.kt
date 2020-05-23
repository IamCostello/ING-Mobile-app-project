package com.example.kotlinpostapi.util

enum class State {
    RUNNING,
    SUCCESS,
    FAILED
}

data class NetworkState (
    val status: State,
    val errMassage: String? = null
) {
  companion object {
      val LOADED = NetworkState(State.SUCCESS)
      val LOADING = NetworkState(State.RUNNING)
      fun error(errMassage: String?) = NetworkState(State.FAILED, errMassage)
  }
}

