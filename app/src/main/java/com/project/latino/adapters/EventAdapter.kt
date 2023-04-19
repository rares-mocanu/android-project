package com.project.latino.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.latino.R
import com.project.latino.models.EventModel

class EventAdapter(private val eventList: ArrayList<EventModel>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameView : TextView = itemView.findViewById(R.id.nameView)
        val detailsView : TextView = itemView.findViewById(R.id.detailsView)
        val clubView : TextView = itemView.findViewById(R.id.clubView)
        val picturesList : RecyclerView = itemView.findViewById(R.id.picturesList)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_event,parent,false)
        return EventViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val currentEvent = eventList[position]

        holder.nameView.text = currentEvent.name
        holder.detailsView.text = currentEvent.details
        holder.clubView.text = currentEvent.club

        val layoutManager = LinearLayoutManager(holder.picturesList.context, LinearLayoutManager.HORIZONTAL, false)
        holder.picturesList.layoutManager = layoutManager
        holder.picturesList.adapter = PicturesAdapter(currentEvent.pictures)
    }
}