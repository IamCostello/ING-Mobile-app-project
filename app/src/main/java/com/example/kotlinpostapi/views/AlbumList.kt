package com.example.kotlinpostapi.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpostapi.Albums.AlbumAdapter
import com.example.kotlinpostapi.Albums.AlbumViewModel
import com.example.kotlinpostapi.apiObjects.Album
import com.example.kotlinpostapi.databinding.FragmentAlbumListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.kotlinpostapi.R

class AlbumList() : Fragment(), AlbumAdapter.OnAlbumClickListener{

    private val viewModel : AlbumViewModel by viewModel()
    private lateinit var binding: FragmentAlbumListBinding
    private lateinit var albumsAdapter: AlbumAdapter
    private lateinit var album: Album

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAlbumListBinding.inflate(inflater, container, false)

        setupRecyclerView()
        observeLiveData()
        getAlbums()

        return binding.root
    }

    private fun setupRecyclerView() {
        val rView: RecyclerView = binding.albumsView
        val layoutManager: LinearLayoutManager = LinearLayoutManager(activity)
        rView.layoutManager = layoutManager

        albumsAdapter = AlbumAdapter(listOf(),this)
        rView.adapter = albumsAdapter
    }

    private fun observeLiveData() {
        viewModel.isErrorLiveData.observe(viewLifecycleOwner, Observer { onReceivedError() })
        viewModel.albumLiveData.observe(viewLifecycleOwner, Observer { onAlbumReceived(it) })
        //viewModel.postLiveData.observe(viewLifecycleOwner, Observer { onPostReceived(it) })
    }

    private fun onReceivedError() {
        android.app.AlertDialog.Builder(activity).setTitle("Błąd").setCancelable(false)
            .setNegativeButton("Anuluj") { _,
                                           _ ->
                activity?.finish()
            }.setPositiveButton("Spróbuj ponownie") { _, _ -> getAlbums() }.show()
    }

    private fun onAlbumReceived(albums: List<Album>) {
        binding.album = albums.first()
        albumsAdapter.updateAlbums(albums)
    }



    private fun getAlbums() {
        viewModel.getAlbums()
    }

//    override fun onAlbumClick(albumId: Int?) {
//        findNavController().navigate(R.id.action_albumList_to_photo)
//    }

    override fun onAlbumClick(album: Album) {
        val action = album.id?.let{PostListDirections.actionPostListToCommentsList(it)}
        if(action != null)
            findNavController().navigate(R.id.action_albumList_to_photo)
    }


}