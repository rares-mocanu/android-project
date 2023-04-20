package com.project.latino.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.latino.R

class TrainerAdapter(private val trainerList: ArrayList<String>) : RecyclerView.Adapter<TrainerAdapter.ClubEventViewHolder> (){

    class ClubEventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textview : TextView = itemView.findViewById(R.id.trainerNameView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClubEventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.traineritemlayout,parent,false)
        return ClubEventViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trainerList.size
    }

    override fun onBindViewHolder(holder: ClubEventViewHolder, position: Int) {
        holder.textview.text=trainerList[position]
    }

}