package com.example.kotlinpostapi.Albums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpostapi.apiObjects.Album
import com.example.kotlinpostapi.databinding.AlbumViewBinding
import kotlinx.android.synthetic.main.album_view.view.*


class AlbumAdapter(albums : List<Album>,
                    var onPhotoClickListener: OnPhotoClickListener) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    private var albums : List<Album> = albums

    inner class AlbumViewHolder(binding: AlbumViewBinding, OnPhotoClickListener: OnPhotoClickListener) : RecyclerView.ViewHolder(binding.root) {
        private val binding: AlbumViewBinding = binding
        private val onAlbumClickListener  = OnPhotoClickListener

        init {
            itemView.show_photos_button.setOnClickListener{onAlbumClickListener.onAlbumClick(albums[adapterPosition])}
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

    interface OnPhotoClickListener{
        fun onAlbumClick(album: Album)
    }

}