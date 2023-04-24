package com.project.latino.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.latino.R
import com.project.latino.models.EventModel
import java.text.SimpleDateFormat
import java.util.*

class EventAdapter(private val eventList: ArrayList<EventModel>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameView: TextView = itemView.findViewById(R.id.nameView)
        val detailsView: TextView = itemView.findViewById(R.id.detailsView)
        val clubView: TextView = itemView.findViewById(R.id.clubView)
        val dateView: TextView = itemView.findViewById(R.id.dateView)
        val timeView: TextView = itemView.findViewById(R.id.timeView)
        val picturesList: RecyclerView = itemView.findViewById(R.id.picturesList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_event, parent, false)
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

        // Format the event date and time as strings
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val dateStr = dateFormat.format(currentEvent.eventDate.time)
        val timeStr = timeFormat.format(currentEvent.eventTime.time)
        holder.dateView.text = dateStr
        holder.timeView.text = timeStr

        val layoutManager = LinearLayoutManager(holder.picturesList.context, LinearLayoutManager.HORIZONTAL, false)
        holder.picturesList.layoutManager = layoutManager
        holder.picturesList.adapter = PicturesAdapter(currentEvent.pictures)
    }
}
