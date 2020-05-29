package com.example.kotlinpostapi.Albums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpostapi.Navigation
import com.example.kotlinpostapi.apiObjects.Album
import com.example.kotlinpostapi.databinding.AlbumViewBinding
import kotlinx.android.synthetic.main.album_view.view.*


class AlbumAdapter(private var albums: List<Album>, var onPhotoClickListener: Navigation.OnPhotoClickListener)
    : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    inner class AlbumViewHolder(private val binding: AlbumViewBinding, OnPhotoClickListener: Navigation.OnPhotoClickListener)
        : RecyclerView.ViewHolder(binding.root) {

        private val onAlbumClickListener  = OnPhotoClickListener

        init {
            itemView.album_view_icon.setOnClickListener{onAlbumClickListener.onPhotoClick(albums[adapterPosition].id)}
            itemView.album_title.setOnClickListener { onAlbumClickListener.onPhotoClick(albums[adapterPosition].id) }
        }


        fun bind(album: Album){
            binding.album = album
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AlbumViewBinding.inflate(layoutInflater)
        return AlbumViewHolder(binding, onPhotoClickListener)
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) = holder.bind(albums[position])

    fun updateAlbums(albums: List<Album>){
        this.albums = albums
        notifyDataSetChanged()
    }

}