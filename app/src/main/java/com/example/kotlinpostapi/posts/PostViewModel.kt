package com.example.kotlinpostapi.posts

import androidx.lifecycle.*
import com.example.kotlinpostapi.apiObjects.Post
import com.example.kotlinpostapi.repository.CommentsRepository
import com.example.kotlinpostapi.repository.PostRepository
import com.example.kotlinpostapi.repository.UserRepository
import com.example.kotlinpostapi.util.Listing

class PostViewModel(private val postRepository: PostRepository, private val userRepository: UserRepository, private val commentsRepository: CommentsRepository) : ViewModel() {
    private val result = MutableLiveData<Listing<Post>>().apply { value = postRepository.getPostFactory() }

    val posts = result.switchMap { it.pagedList }
    val networkState = result.switchMap { it.networkState }
    val refreshState = result.switchMap { it.refreshState }

    fun refresh() {
        result.value?.refresh?.invoke()
    }

    fun retry() {
        val listing = result.value
        listing?.retry?.invoke()
    }

    fun addPost(post: Post) {
        postRepository.createPost(post)
    }
}