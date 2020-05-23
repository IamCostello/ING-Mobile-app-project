package com.example.kotlinpostapi.util

import android.graphics.Color
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@BindingAdapter("networkStatus")
fun bindNetworkStatus(textView: TextView, networkState: NetworkState?) {
    println("network binding")
    when(networkState) {
        NetworkState.LOADED -> {textView.setBackgroundColor(Color.GREEN)}
        NetworkState.LOADING -> {textView.setBackgroundColor(Color.YELLOW)}
        NetworkState.error("Failed loading data") -> {textView.setBackgroundColor(Color.RED)}
    }
}

@BindingAdapter("networkStatusSpinner")
fun bindNetworkStatusSpinner(progressBar: ProgressBar, networkState: NetworkState?) {
    when(networkState) {
        NetworkState.LOADED -> {progressBar.visibility = View.GONE}
        NetworkState.LOADING -> {progressBar.visibility = View.VISIBLE}
        NetworkState.error("Failed loading data") -> {progressBar.visibility = View.GONE}
    }
}