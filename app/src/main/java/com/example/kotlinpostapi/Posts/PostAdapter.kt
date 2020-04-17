package com.example.kotlinpostapi.Posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpostapi.apiObjects.Post
import com.example.kotlinpostapi.databinding.PostViewBinding

class PostAdapter(posts : List<Post>, onUserClickListener: OnUserClickListener) : RecyclerView.Adapter<PostAdapter.PostsViewHolder>(){

    private var posts : List<Post> = posts
    var onUserClickListener = onUserClickListener

    inner class PostsViewHolder(binding: PostViewBinding, OnUserClickListener: OnUserClickListener) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private val binding: PostViewBinding = binding
        private val onUserClickListener = OnUserClickListener

        init{
            itemView.setOnClickListener(this)
        }

        fun bind(post: Post){
            binding.post = post
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            onUserClickListener.onUserClick(posts[adapterPosition].userId)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PostViewBinding.inflate(layoutInflater)

        return PostsViewHolder(binding, onUserClickListener)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) = holder.bind(posts[position])

    fun updatePosts(posts: List<Post>){
        this.posts = posts
        notifyDataSetChanged()
    }

    interface OnUserClickListener{
        fun onUserClick(userId: Int?)
    }
}