package com.project.latino.ui.fragments

import com.project.latino.adapters.EventAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.latino.R
import com.project.latino.models.EventModel
import com.project.latino.databinding.FragmentEventBinding
import java.util.Calendar


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

        val date1 = Calendar.getInstance()
        val date2 = Calendar.getInstance()
        val date3 = Calendar.getInstance()

        date1.set(2024, Calendar.JUNE, 20)
        date2.set(2023, Calendar.APRIL, 12)
        date3.set(2023, Calendar.DECEMBER, 6)

        val time1 = Calendar.getInstance()
        time1.set(Calendar.HOUR_OF_DAY, 20)
        time1.set(Calendar.MINUTE, 0)
        time1.set(Calendar.SECOND, 0)

        val events = ArrayList<EventModel>(100)
        val pictures = listOf(R.drawable.rio, R.drawable.rio, R.drawable.rio)
        events.add(EventModel(1, date1, time1,"Event 1", "Details of Event 1", pictures, "Club A"))
        events.add(EventModel(2, date2, time1,"Event 2", "Details of Event 2", pictures, "Club B"))
        events.add(EventModel(3, date3, time1,"Event 3", "Details of Event 3", pictures, "Club C"))

        val sortedEvents = ArrayList(events.sortedBy { it.eventDate })

        eventRecyclerView.adapter = EventAdapter(sortedEvents)
    }

}
