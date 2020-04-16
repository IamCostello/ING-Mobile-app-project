package com.example.kotlinpostapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpostapi.Posts.PostAdapter
import com.example.kotlinpostapi.Posts.PostViewModel
import com.example.kotlinpostapi.apiObjects.Post
import com.example.kotlinpostapi.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: PostViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding

    private lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupRecyclerView()
        observeLiveData()
        getPosts()
    }

    private fun setupRecyclerView() {
        val rView: RecyclerView = binding.postsView
        val layoutManager: LinearLayoutManager = LinearLayoutManager(this)
        rView.layoutManager = layoutManager

        postAdapter = PostAdapter(listOf())
        rView.adapter = postAdapter
    }

    private fun observeLiveData() {
        viewModel.isErrorLiveData.observe(this, Observer { onReceivedError() })
        viewModel.postsLiveData.observe(this, Observer { onPostsReceived(it) })
    }

    private fun getPosts() {
        viewModel.getPosts()
    }

    private fun onReceivedError() {
        android.app.AlertDialog.Builder(this).setTitle("Błąd").setCancelable(false)
            .setNegativeButton("Anuluj") { _,
                                           _ ->
                finish()
            }.setPositiveButton("Spróbuj ponownie") { _, _ -> getPosts() }.show()
    }

    private fun onPostsReceived(posts: List<Post>) {
        binding.post = posts.first()
        postAdapter.updatePosts(posts)
    }
}
