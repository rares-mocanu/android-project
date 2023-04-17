package com.project.latino.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.latino.R
import com.project.latino.models.SchoolModel

class SchoolAdapter(private val schoollist: ArrayList<SchoolModel>) : RecyclerView.Adapter<SchoolAdapter.SchoolViewHolder>() {

    class SchoolViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nameView : TextView = itemView.findViewById(R.id.nameView)
        val imageView : ImageView = itemView.findViewById(R.id.imageView)
        val trainerList : RecyclerView = itemView.findViewById(R.id.trainerList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.schoolitemlayout,parent,false)
        return SchoolViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return schoollist.size
    }

    override fun onBindViewHolder(holder: SchoolViewHolder, position: Int) {
    val currentschool = schoollist[position]

    holder.nameView.text = currentschool.name
    holder.imageView.setImageResource(currentschool.logo)
    val layoutManager = LinearLayoutManager(holder.trainerList.context)
    holder.trainerList.layoutManager=layoutManager
    holder.trainerList.adapter= TrainerAdapter(currentschool.trainers)
    }

}