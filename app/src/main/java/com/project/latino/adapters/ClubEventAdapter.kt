package com.project.latino.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.latino.R

class ClubEventAdapter(private val eventList: ArrayList<String>) : RecyclerView.Adapter<ClubEventAdapter.ClubEventViewHolder> () {

    class ClubEventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textview: TextView = itemView.findViewById(R.id.clubEventNameView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubEventViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.clubeventitemlayout, parent, false)
        return ClubEventViewHolder(view)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    override fun onBindViewHolder(holder: ClubEventViewHolder, position: Int) {
        holder.textview.text = eventList[position]
    }
}