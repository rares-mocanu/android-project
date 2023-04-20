package com.project.latino.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.latino.R
import com.project.latino.adapters.EventAdapter
import com.project.latino.models.EventModel
import com.project.latino.databinding.FragmentEventBinding


class EventFragment : Fragment() {

    private lateinit var binding: FragmentEventBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val eventRecyclerView = binding.picturesList
        val layoutManager = LinearLayoutManager(requireContext())
        eventRecyclerView.layoutManager = layoutManager

        val events = ArrayList<EventModel>(100)
        val pictures = listOf(R.drawable.rio, R.drawable.rio, R.drawable.rio)
        events.add(EventModel(1, "Event 1", "Details of Event 1", pictures, "Club A"))
        events.add(EventModel(2, "Event 2", "Details of Event 2", pictures, "Club B"))
        events.add(EventModel(3, "Event 3", "Details of Event 3", pictures, "Club C"))

        eventRecyclerView.adapter = EventAdapter(events)
    }

}
