package com.example.kotlinpostapi.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinpostapi.apiObjects.Photo
import com.example.kotlinpostapi.databinding.PhotoViewBinding

class PhotoAdapter(photos: List<Photo>) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>(){

    private var photos : List<Photo> = photos

    inner class PhotoViewHolder(binding: PhotoViewBinding) : RecyclerView.ViewHolder(binding.root){
        private val binding : PhotoViewBinding = binding

        fun bind(photo: Photo){
            binding.photo = photo
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PhotoViewBinding.inflate(layoutInflater)
        return PhotoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) = holder.bind(photos[position])

    fun updatePhoto(photos: List<Photo>){
        this.photos = photos
        notifyDataSetChanged()
    }
}