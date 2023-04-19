package com.project.latino.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.latino.R
import com.project.latino.adapters.ClubsAdapter
import com.project.latino.databinding.ActivityClubsBinding
import com.project.latino.models.ClubModel

class ClubsFragment : Fragment() {

    private lateinit var binding: ActivityClubsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityClubsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val clubsRecyclerView = binding.clubsRecyclerView
        val layoutManager = LinearLayoutManager(requireContext())

        clubsRecyclerView.layoutManager = layoutManager
        val clubsList = ArrayList<ClubModel>(100)
        var clubEventsList =  ArrayList<String>()
        clubEventsList.addAll(listOf("Palladium party","Kiz party"))
        clubsList.add(ClubModel(0,"Rio",clubEventsList,R.drawable.rio))
        clubEventsList =  ArrayList<String>()
        clubEventsList.addAll(listOf("Latin Experience","Q-Ban Project"))
        clubsList.add(ClubModel(1,"V Lounge",clubEventsList,R.drawable.latinexperience))

        clubsRecyclerView.adapter = ClubsAdapter(clubsList)
    }
}