package com.example.kotlinpostapi.Posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpostapi.apiObjects.Post
import com.example.kotlinpostapi.databinding.PostViewBinding

class PostAdapter(posts : List<Post>) : RecyclerView.Adapter<PostAdapter.PostsViewHolder>(){

    private var posts : List<Post> = posts

    inner class PostsViewHolder(binding: PostViewBinding) : RecyclerView.ViewHolder(binding.root) {
        private val binding: PostViewBinding = binding

        fun bind(post: Post){
            binding.post = post
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PostViewBinding.inflate(layoutInflater)
        return PostsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) = holder.bind(posts[position])

    fun updatePosts(posts: List<Post>){
        this.posts = posts
        notifyDataSetChanged()
    }

}