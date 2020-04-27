package com.example.kotlinpostapi.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpostapi.apiObjects.Album
import com.example.kotlinpostapi.apiObjects.Photo
import com.example.kotlinpostapi.databinding.FragmentPhotoListBinding
import com.example.kotlinpostapi.photos.PhotoAdapter
import com.example.kotlinpostapi.photos.PhotoListViewModel
import com.example.kotlinpostapi.util.PostListDecorator
import org.koin.androidx.viewmodel.ext.android.viewModel


class PhotoList : Fragment() {

    private val viewModel: PhotoListViewModel by viewModel()
    private lateinit var binding: FragmentPhotoListBinding
    private lateinit var photosAdapter: PhotoAdapter
    private lateinit var album: Album

    val args: PhotoListArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPhotoListBinding.inflate(inflater, container, false)

        setupRecyclerView()
        observeLiveData()
        getAlbum(args.albumId)
        return binding.root
    }


    private fun setupRecyclerView() {
        val rView: RecyclerView = binding.photoView
        val layoutManager = GridLayoutManager(activity, GridLayoutManager.VERTICAL)
        rView.layoutManager = layoutManager
        rView.addItemDecoration(PostListDecorator(12,24))

        photosAdapter = PhotoAdapter(listOf())
        rView.adapter = photosAdapter
    }

    private fun observeLiveData() {
        viewModel.isErrorLiveData.observe(viewLifecycleOwner, Observer { onReceivedError() })
        viewModel.photosLiveData.observe(viewLifecycleOwner, Observer { onPhotosReceived(it) })
        viewModel.albumLiveData.observe(viewLifecycleOwner, Observer { onAlbumReceived(it) })
    }

    private fun onReceivedError() {
        android.app.AlertDialog.Builder(activity).setTitle("Błąd").setCancelable(false)
            .setNegativeButton("Anuluj") { _,
                                           _ ->
                activity?.finish()
            }.setPositiveButton("Spróbuj ponownie") { _, _ -> getPhotos(album) }.show()
    }

    private fun onPhotosReceived(photos: List<Photo>) {
        photosAdapter.updatePhoto(photos)
    }

    private fun onAlbumReceived(album: Album){
        this.album = album
        binding.album = album
        getPhotos(album)
    }
    private fun getPhotos(album: Album) {
        viewModel.getPhotos(album)
    }

    private fun getAlbum(albumId: Int){
        viewModel.getAlbum(albumId)
    }
}
