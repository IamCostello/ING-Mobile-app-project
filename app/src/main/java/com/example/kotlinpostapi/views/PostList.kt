package com.example.kotlinpostapi.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpostapi.Navigation
import com.example.kotlinpostapi.Posts.PostAdapter
import com.example.kotlinpostapi.Posts.PostViewModel
import com.example.kotlinpostapi.apiObjects.Post
import com.example.kotlinpostapi.apiObjects.User
import com.example.kotlinpostapi.databinding.FragmentPostListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostList() : Fragment(), Navigation.OnUserClickListener, Navigation.OnPostClickListener{

    private val viewModel: PostViewModel by viewModel()

    private lateinit var binding: FragmentPostListBinding

    private lateinit var postAdapter: PostAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPostListBinding.inflate(inflater, container, false)

        setupRecyclerView()
        observeLiveData()
        getPosts()

        return binding.root
    }

    private fun setupRecyclerView() {
        val rView: RecyclerView = binding.postsView
        val layoutManager: LinearLayoutManager = LinearLayoutManager(activity)
        rView.layoutManager = layoutManager

        postAdapter = PostAdapter(listOf(),this, this)
        rView.adapter = postAdapter
    }

    private fun observeLiveData() {
        viewModel.isErrorLiveData.observe(viewLifecycleOwner, Observer { onReceivedError() })
        viewModel.postsLiveData.observe(viewLifecycleOwner, Observer { onPostsReceived(it) })
    }

    private fun getPosts() {
        viewModel.getPosts()
    }

    private fun onReceivedError() {
        android.app.AlertDialog.Builder(activity).setTitle("Błąd").setCancelable(false)
            .setNegativeButton("Anuluj") { _,
                                           _ ->
                activity?.finish()
            }.setPositiveButton("Spróbuj ponownie") { _, _ -> getPosts() }.show()
    }

    private fun onPostsReceived(posts: List<Post>) {
        binding.post = posts.first()
        postAdapter.updatePosts(posts)
    }

    override fun onUserClick(userId: Int?) {
        val action = userId?.let { PostListDirections.actionPostListToUserInfo(it)}
        if (action != null) {
            findNavController().navigate(action)
        }
    }

    override fun onPostClick(post: Post) {
        val action = post.id?.let { PostListDirections.actionPostListToCommentsList(it) }
        if (action != null) {
            findNavController().navigate(action)
        }
    }
}
