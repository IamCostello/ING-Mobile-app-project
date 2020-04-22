package com.example.kotlinpostapi.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpostapi.Albums.AlbumAdapter
import com.example.kotlinpostapi.Albums.AlbumViewModel
import com.example.kotlinpostapi.Navigation
import com.example.kotlinpostapi.apiObjects.Album
import com.example.kotlinpostapi.apiObjects.User
import com.example.kotlinpostapi.databinding.FragmentAlbumListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumList() : Fragment(), Navigation.OnPhotoClickListener{

    private val viewModel : AlbumViewModel by viewModel()
    private lateinit var binding: FragmentAlbumListBinding
    private lateinit var albumsAdapter: AlbumAdapter
    private lateinit var user: User

    val args: AlbumListArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAlbumListBinding.inflate(inflater, container, false)

        setupRecyclerView()
        observeLiveData()
        getUser(args.userId)

        return binding.root
    }

    private fun setupRecyclerView() {
        val rView: RecyclerView = binding.albumsView
        val layoutManager = LinearLayoutManager(activity)
        rView.layoutManager = layoutManager

        albumsAdapter = AlbumAdapter(listOf(),this)
        rView.adapter = albumsAdapter
    }

    private fun observeLiveData() {
        viewModel.isErrorLiveData.observe(viewLifecycleOwner, Observer { onReceivedError() })
        viewModel.albumLiveData.observe(viewLifecycleOwner, Observer { onAlbumReceived(it) })
        viewModel.userLiveData.observe(viewLifecycleOwner, Observer { onUserReceived(it) })
    }

    private fun onReceivedError() {
        android.app.AlertDialog.Builder(activity).setTitle("Błąd").setCancelable(false)
            .setNegativeButton("Anuluj") { _,
                                           _ ->
                activity?.finish()
            }.setPositiveButton("Spróbuj ponownie") { _, _ -> getAlbum(user) }.show()
    }

    private fun onAlbumReceived(albums: List<Album>) {
        albumsAdapter.updateAlbums(albums)
    }


    private fun onUserReceived(user: User) {
        this.user = user
        binding.user = user
        getAlbum(user)
    }

    private fun getAlbum(user: User){
        viewModel.getAlbums(user)
    }

    private fun getUser(userId: Int) {
        viewModel.getUser(userId)
    }

    override fun onPhotoClick(albumId: Int?) {
        val action = albumId?.let{AlbumListDirections.actionAlbumsListToPhotoList(it)}
        if(action != null)
            findNavController().navigate(action)
    }


}