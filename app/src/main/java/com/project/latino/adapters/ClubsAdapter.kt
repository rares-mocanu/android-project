package com.project.latino.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.latino.AppDatabase
import com.project.latino.R
import com.project.latino.models.ClubModel
import com.project.latino.models.EventModel
import com.project.latino.ui.fragments.ClubsFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newCoroutineContext
import kotlinx.coroutines.withContext

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
        CoroutineScope(Dispatchers.Main).launch{
            val instance= AppDatabase.getInstance(holder.itemView.context)
            var eventlist= ArrayList<EventModel>(instance?.eventDao()!!.getEventsOfClub(currentclub.clubID))
            println(eventlist)
            holder.clubeventlist.adapter = ClubEventAdapter(eventlist)
        }

    }
}