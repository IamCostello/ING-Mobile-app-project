package com.example.kotlinpostapi.Posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpostapi.apiObjects.Post
import com.example.kotlinpostapi.databinding.PostViewBinding
import kotlinx.android.synthetic.main.post_view.view.*
import com.example.kotlinpostapi.Navigation.*
import com.example.kotlinpostapi.apiObjects.User

class PostAdapter(
    private var posts: List<Post>,
    var onUserClickListener: OnUserClickListener,
    var onPostClickListener: OnPostClickListener
) : RecyclerView.Adapter<PostAdapter.PostsViewHolder>(){

    inner class PostsViewHolder(binding: PostViewBinding, OnUserClickListener: OnUserClickListener, OnPostClickListener: OnPostClickListener) : RecyclerView.ViewHolder(binding.root) {
        private val binding: PostViewBinding = binding
        private val onUserClickListener = OnUserClickListener
        private val onPostClickListener = OnPostClickListener

        init{
            itemView.username.setOnClickListener { onUserClickListener.onUserClick(posts[adapterPosition].userId) }
            itemView.show_comments_button.setOnClickListener{ onPostClickListener.onPostClick(posts[adapterPosition]) }
        }

        fun bind(post: Post){
            binding.post = post
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PostViewBinding.inflate(layoutInflater)

        return PostsViewHolder(binding, onUserClickListener, onPostClickListener)
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