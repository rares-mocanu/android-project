package com.project.latino.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.latino.R
import com.project.latino.models.ClubModel

class ClubsAdapter (private val clubslist: ArrayList<ClubModel>) : RecyclerView.Adapter<ClubsAdapter.ClubViewHolder>() {

    class ClubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameView: TextView = itemView.findViewById(R.id.nameView)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val clubeventlist: RecyclerView = itemView.findViewById(R.id.clubEventList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.clubitemlayout, parent, false)
        return ClubViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return clubslist.size
    }

    override fun onBindViewHolder(holder: ClubViewHolder, position: Int) {
        val currentclub = clubslist[position]
        holder.nameView.text = currentclub.name
        holder.imageView.setImageResource(currentclub.logo)
        val layoutManager = LinearLayoutManager(holder.clubeventlist.context)
        holder.clubeventlist.layoutManager = layoutManager
        holder.clubeventlist.adapter = ClubEventAdapter(currentclub.events)
    }
}