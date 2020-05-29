package com.example.kotlinpostapi.Posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpostapi.Navigation.OnPostClickListener
import com.example.kotlinpostapi.Navigation.OnUserClickListener
import com.example.kotlinpostapi.apiObjects.Post
import com.example.kotlinpostapi.databinding.PostViewBinding
import com.example.kotlinpostapi.util.NetworkState
import com.example.kotlinpostapi.util.PostDiffUtil
import kotlinx.android.synthetic.main.post_view.view.*


class PostAdapter(
    var onUserClickListener: OnUserClickListener,
    var onPostClickListener: OnPostClickListener
) : PagedListAdapter<Post, PostAdapter.PostsViewHolder>(PostDiffUtil) {

    private var networkState: NetworkState? = null

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

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        val post: Post? = getItem(position)
        if (post != null) {
            holder.bind(post)
            holder.itemView.username.setOnClickListener { onUserClickListener.onUserClick(post.userId) }
            holder.itemView.show_comments_button.setOnClickListener { onPostClickListener.onPostClick(post) }
            holder.itemView.comment_icon.setOnClickListener { onPostClickListener.onPostClick(post) }
        }
    }
}