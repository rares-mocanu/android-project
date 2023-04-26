package com.project.latino.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.project.latino.R

class PicturesAdapter(private val picturesList: List<Int>) : RecyclerView.Adapter<PicturesAdapter.PicturesViewHolder>() {

    class PicturesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.pictureView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicturesViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.pictureitemlayout, parent, false)
        return PicturesViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return picturesList.size
    }

    override fun onBindViewHolder(holder: PicturesViewHolder, position: Int) {
        val currentPicture = picturesList[position]
        holder.imageView.setImageResource(currentPicture)
    }
}
