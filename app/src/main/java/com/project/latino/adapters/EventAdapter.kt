package com.project.latino.adapters

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.latino.R
import com.project.latino.models.EventModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class EventAdapter(private val eventList: ArrayList<EventModel>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    interface OnShareButtonClickListener {
        fun onShareButtonClick(event: EventModel)
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameView: TextView = itemView.findViewById(R.id.nameView)
        val detailsView: TextView = itemView.findViewById(R.id.detailsView)
        val clubView: TextView = itemView.findViewById(R.id.clubView)
        val dateView: TextView = itemView.findViewById(R.id.dateView)
        val timeView: TextView = itemView.findViewById(R.id.timeView)
        val picturesList: RecyclerView = itemView.findViewById(R.id.picturesList)
        val shareButton: TextView = itemView.findViewById(R.id.shareButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_event, parent, false)
        val viewHolder = EventViewHolder(itemView)

        viewHolder.shareButton.setOnClickListener {
            val position = viewHolder.adapterPosition
            val event = eventList[position]

            // Create an AlertDialog with two options
            val builder = AlertDialog.Builder(itemView.context)
            builder.setTitle("Share Event")
            builder.setItems(arrayOf("Share Name, Date, Time, and Club", "Share Details")) { dialog, which ->
                when (which) {
                    0 -> {
                        // Share Name, Date, Time, and Club via Text Message
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse("sms:")
                        val message = "Title: ${event.name}\n" +
                                "Date: ${viewHolder.dateView.text}\n" +
                                "Time: ${viewHolder.timeView.text}\n" +
                                "Club Name: ${event.club}"
                        intent.putExtra("sms_body", message)
                        itemView.context.startActivity(Intent.createChooser(intent, "Choose a Text Messaging app"))
                    }
                    1 -> {
                        // Share Details via Text Message
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse("sms:")
                        val message = "Details: ${event.details}"
                        intent.putExtra("sms_body", message)
                        itemView.context.startActivity(Intent.createChooser(intent, "Choose a Text Messaging app"))
                    }
                }
            }
            builder.show()
        }

        return viewHolder
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

