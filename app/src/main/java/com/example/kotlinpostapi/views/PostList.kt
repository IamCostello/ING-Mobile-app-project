package com.example.kotlinpostapi.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.kotlinpostapi.Navigation
import com.example.kotlinpostapi.posts.PostAdapter
import com.example.kotlinpostapi.posts.PostViewModel
import com.example.kotlinpostapi.apiObjects.Post
import com.example.kotlinpostapi.databinding.FragmentPostListBinding
import com.example.kotlinpostapi.util.NetworkState
import com.example.kotlinpostapi.util.PostListDecorator
import kotlinx.android.synthetic.main.fragment_post_list.*
import kotlinx.android.synthetic.main.fragment_post_list.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostList() : Fragment(), Navigation.OnUserClickListener, Navigation.OnPostClickListener{

    private val viewModel: PostViewModel by viewModel()

    private lateinit var binding: FragmentPostListBinding

    private lateinit var postAdapter: PostAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val swiperef = view.swipeRefresh
        setupSwipeRefresh(swiperef)
        observeLiveData()
        setupRecyclerView()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPostListBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    private fun setupRecyclerView() {
        val rView: RecyclerView = binding.postsView

        val layoutManager: GridLayoutManager = GridLayoutManager(activity, GridLayoutManager.VERTICAL)
        rView.layoutManager = layoutManager
        rView.addItemDecoration(PostListDecorator(12, 24))

        postAdapter = PostAdapter(this, this)
        rView.adapter = postAdapter

        binding.viewModel = viewModel
        binding.executePendingBindings()
    }

    private fun setupSwipeRefresh(refresh: SwipeRefreshLayout) {
        viewModel.refreshState.observe(viewLifecycleOwner, Observer {
            swipeRefresh.isRefreshing = it == NetworkState.LOADING
        })
        refresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun observeLiveData() {
        viewModel.posts.observe(viewLifecycleOwner, Observer { onPostsReceived(it) })
        viewModel.networkState.observe(viewLifecycleOwner, Observer { onNetworkStateChange(it) })
    }

    private fun onNetworkStateChange(state: NetworkState) {
        when(state) {
            NetworkState.error("Failed loading data") -> {
                android.app.AlertDialog.Builder(activity, 5)
                    .setTitle("Network error")
                    .setMessage("Something went wrong loading data")
                    .setCancelable(false)
                    .setNegativeButton("Retry") { _,
                                                   _ ->
                        viewModel.retry()
                    }.setPositiveButton("Ok") { _, _ ->  }
                    .show()
            }
        }
    }

    private fun onPostsReceived(posts: PagedList<Post>) {
        postAdapter.submitList(posts)
        println("ON POST RECEIVED ----")
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
