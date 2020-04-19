package com.example.kotlinpostapi.Albums

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpostapi.apiObjects.Album
import com.example.kotlinpostapi.databinding.AlbumViewBinding
import kotlinx.android.synthetic.main.album_view.view.*


class AlbumAdapter(albums : List<Album>,
                    var onAlbumClickListener: OnAlbumClickListener) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    private var albums : List<Album> = albums

    inner class AlbumViewHolder(binding: AlbumViewBinding, OnAlbumClickListener: OnAlbumClickListener) : RecyclerView.ViewHolder(binding.root) {
        private val binding: AlbumViewBinding = binding
        private val onAlbumClickListener  = OnAlbumClickListener

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
        return AlbumViewHolder(binding, onAlbumClickListener)
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) = holder.bind(albums[position])

    fun updateAlbums(albums: List<Album>){
        this.albums = albums
        notifyDataSetChanged()
    }



    interface OnAlbumClickListener{
        fun onAlbumClick(album: Album)
    }

}