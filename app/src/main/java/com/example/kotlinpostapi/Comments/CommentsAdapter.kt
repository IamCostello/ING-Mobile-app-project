package com.example.kotlinpostapi.Comments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpostapi.apiObjects.Comment
import com.example.kotlinpostapi.databinding.CommentViewBinding

class CommentsAdapter(comments : List<Comment>) : RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>(){

    private var comments : List<Comment> = comments

    inner class CommentsViewHolder(binding: CommentViewBinding) : RecyclerView.ViewHolder(binding.root) {
        private val binding: CommentViewBinding = binding

        fun bind(comment: Comment){
            binding.comment = comment
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CommentViewBinding.inflate(layoutInflater)
        return CommentsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) = holder.bind(comments[position])

    fun updateComments(comments: List<Comment>){
        this.comments = comments
        notifyDataSetChanged()
    }
}