package com.project.latino.ui.fragments

import android.app.NotificationManager
import android.content.Context
import com.project.latino.adapters.EventAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.project.latino.AppDatabase
import com.project.latino.R
import com.project.latino.adapters.ClubsAdapter
import com.project.latino.models.EventModel
import com.project.latino.databinding.FragmentEventBinding
import com.project.latino.models.ClubModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar


class EventFragment : Fragment(), EventAdapter.OnShareButtonClickListener {

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

        CoroutineScope(Dispatchers.Main).launch{
            val instance= AppDatabase.getInstance(requireContext())
            var sortedEvents= ArrayList<EventModel>(instance?.eventDao()!!.getAllEvents())
            var clubsList= ArrayList<ClubModel>(instance?.clubDao()!!.getAllClubs())
            if(true)
            {
                sortedEvents.clear()
                instance.eventDao().deleteAll()
                val pictures = ArrayList(listOf(R.drawable.rio, R.drawable.rio, R.drawable.rio))
                var date1=Calendar.getInstance()
                var date2=Calendar.getInstance()
                var date3=Calendar.getInstance()
                var date4=Calendar.getInstance()

                var time=Calendar.getInstance()
                time.set(Calendar.HOUR_OF_DAY, 20)
                time.set(Calendar.MINUTE, 0)
                time.set(Calendar.SECOND, 0)
                date1.set(2023, Calendar.APRIL, 20)
                sortedEvents.add(
                    EventModel(1,date1,time,"Palladium Party","details",pictures,clubsList[0].clubID)
                )
                date2.set(2024, Calendar.DECEMBER, 26)
                sortedEvents.add(
                    EventModel(2,date2,time,"Kiz Party","details",pictures,clubsList[0].clubID)
                )
                date3.set(2024, Calendar.FEBRUARY, 28)
                sortedEvents.add(
                    EventModel(3,date3,time,"Latin Experience","details",pictures,clubsList[1].clubID)
                )
                date4.set(2023, Calendar.MAY, 7)
                sortedEvents.add(
                    EventModel(4,date4,time,"Q-Ban Project","details",pictures,clubsList[1].clubID)
                )
                for(e in sortedEvents)
                {
                    println(e.id)
                    instance.eventDao().insertEvent(e)
                }
            }

            eventRecyclerView.adapter = EventAdapter(sortedEvents)
        }

    }

    override fun onShareButtonClick(event: EventModel) {
        val bottomSheet = LayoutInflater.from(context).inflate(R.layout.bottom_sheet, null)

        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(bottomSheet)

        val shareDetailsButton = bottomSheet.findViewById<Button>(R.id.btn_share_details)
        shareDetailsButton.setOnClickListener {
            // Handle share details button click here
            dialog.dismiss()
        }

        val sharePhotosButton = bottomSheet.findViewById<Button>(R.id.btn_share_photos)
        sharePhotosButton.setOnClickListener {
            // Handle share photos button click here
            dialog.dismiss()
        }

        dialog.show()
    }



}

