package com.project.latino.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.project.latino.databinding.HomepageItemBinding
import com.project.latino.models.HPItemModel

class CustomAdapter(
    private val list: List<HPItemModel>,
    private val onItemClick: (HPItemModel) -> Unit
) : RecyclerView.Adapter<CustomAdapter.HPHolder>() {

    lateinit var binding: HomepageItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HPHolder {
        binding = HomepageItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return HPHolder(binding)
    }

    override fun onBindViewHolder(holder: HPHolder, position: Int) {
        val item = list[position]
        holder.image.setImageDrawable(
            ContextCompat.getDrawable(
                holder.image.context, item.image
            )
        )

        holder.title.text = item.title
        holder.constraint.setOnClickListener {
            onItemClick(item)
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }

    class HPHolder(binding: HomepageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.title
        val image = binding.image
        val constraint = binding.constraint
    }

}