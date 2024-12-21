package com.example.photoapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photoapp.R
import com.example.photoapp.ui.models.ListItemDataModel

class PhotoAdapter(private var photos: List<ListItemDataModel>) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return PhotoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int = photos.size

    fun updateData(newPhotos: List<ListItemDataModel>) {
        photos = newPhotos
        notifyDataSetChanged() // Notify adapter of data change
    }

    class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val photoTitle: TextView = itemView.findViewById(R.id.text_title)
        private val albumTitle: TextView = itemView.findViewById(R.id.text_album_name)
        private val userName: TextView = itemView.findViewById(R.id.text_user_name)
        private val thumbnail: ImageView = itemView.findViewById(R.id.image_thumbnail)

        fun bind(item: ListItemDataModel) {
            // Bind photo thumbnail using Glide
            photoTitle.text = item.photoTitle
            photoTitle.setSelected(true);
            albumTitle.text = item.albumTitle
            userName.text = item.userName

            // Load thumbnail with Glide or another image loading library
            Glide.with(itemView.context)
                .load(item.thumbnailUrl)
                .placeholder(R.drawable.ic_launcher_background) // Use your placeholder image
                .into(thumbnail)
        }
    }
}

