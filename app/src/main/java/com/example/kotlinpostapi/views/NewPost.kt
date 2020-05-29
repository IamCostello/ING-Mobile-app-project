package com.example.kotlinpostapi.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kotlinpostapi.Navigation
import com.example.kotlinpostapi.apiObjects.Post
import com.example.kotlinpostapi.databinding.FragmentAddPostBinding
import com.example.kotlinpostapi.firebase.FirebaseHelper
import com.example.kotlinpostapi.repository.PostRepository
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.android.inject


class NewPost:  Fragment(), Navigation.OnAddPostClickListener {
    private lateinit var title: String
    private lateinit var description: String
    private lateinit var binding: FragmentAddPostBinding
    private val postRepository: PostRepository by inject()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPostBinding.inflate(inflater,container,false)
        binding.addPostImageView.setOnClickListener(View.OnClickListener { onAddPostClick() })


        return binding.root
    }

    override fun onAddPostClick() {
        title = binding.postTitle.text.toString()
        description = binding.postDescription.text.toString()
        postRepository.createPost(Post(null,null,null,title,description,getNameFromEmail(),0))


        Log.d("EMAIL TO",getNameFromEmail())
    }

    fun getNameFromEmail():String{
        Log.d("EMAIL TO", FirebaseHelper.getCurrentUser()?.email.toString())
        val x = FirebaseHelper.getCurrentUser()?.email.toString().indexOf("@")
        if(x > 0) {
            return FirebaseHelper.getCurrentUser()?.email.toString().substring(0, x)
        }
        return "anonymous"
    }
}