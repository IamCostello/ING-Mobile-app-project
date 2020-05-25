package com.example.kotlinpostapi.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinpostapi.apiObjects.Photo
import com.example.kotlinpostapi.databinding.PhotoViewBinding

class PhotoAdapter(private var photos: List<Photo>) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>(){

    inner class PhotoViewHolder(private val binding: PhotoViewBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(photo: Photo){
            binding.photo.url = photo.url
            binding.executePendingBindings()

            
            Glide.with()
                .load(photo.url)
                .into(binding.photoImageView)
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