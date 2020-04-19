package com.example.kotlinpostapi.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpostapi.Comments.CommentsAdapter
import com.example.kotlinpostapi.Comments.CommentsListViewModel
import com.example.kotlinpostapi.apiObjects.Comment
import com.example.kotlinpostapi.apiObjects.Post
import com.example.kotlinpostapi.databinding.FragmentCommentsListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class CommentsList : Fragment() {
    private val viewModel: CommentsListViewModel by viewModel()

    private lateinit var binding: FragmentCommentsListBinding

    private lateinit var commentsAdapter: CommentsAdapter

    private lateinit var post: Post

    val args: CommentsListArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCommentsListBinding.inflate(inflater, container, false)

        setupRecyclerView()
        observeLiveData()
        getPost(args.postId)

        return binding.root
    }


    private fun setupRecyclerView() {
        val rView: RecyclerView = binding.commentsView
        val layoutManager: LinearLayoutManager = LinearLayoutManager(activity)
        rView.layoutManager = layoutManager

        commentsAdapter = CommentsAdapter(listOf())
        rView.adapter = commentsAdapter
    }

    private fun observeLiveData() {
        viewModel.isErrorLiveData.observe(viewLifecycleOwner, Observer { onReceivedError() })
        viewModel.commentsLiveData.observe(viewLifecycleOwner, Observer { onCommentsReceived(it) })
        viewModel.postLiveData.observe(viewLifecycleOwner, Observer { onPostReceived(it) })
    }

    private fun onReceivedError() {
        android.app.AlertDialog.Builder(activity).setTitle("Błąd").setCancelable(false)
            .setNegativeButton("Anuluj") { _,
                                           _ ->
                activity?.finish()
            }.setPositiveButton("Spróbuj ponownie") { _, _ -> getComments(post) }.show()
    }

    private fun onCommentsReceived(comments: List<Comment>) {
        commentsAdapter.updateComments(comments)
    }

    private fun onPostReceived(post: Post){
        this.post = post
        binding.post = post
        getComments(post)
    }
    private fun getComments(post: Post) {
        viewModel.getComments(post)
    }
    private fun getPost(postId: Int){
        viewModel.getPost(postId)
    }
}
