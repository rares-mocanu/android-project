package com.project.latino.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.latino.MainActivity
import com.project.latino.R
import com.project.latino.SchoolActivity
import com.project.latino.adapters.CustomAdapter
import com.project.latino.databinding.FragmentHomeBinding
import com.project.latino.models.HPItemModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.selectedItemId = R.id.navigation_home
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CustomAdapter(listOfHPItems()) { model ->
            onClick(model = model)
        }

        binding.recyclerView.adapter = adapter

    }

    private fun onClick(model: HPItemModel) {
        findNavController().popBackStack()
        when (model.title) {
            "Schools" -> {
                findNavController().navigate(R.id.navigation_schools)
                activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.menu?.findItem(R.id.navigation_schools)?.isChecked = true
            }
            "Clubs" -> {
                findNavController().navigate(R.id.navigation_clubs)
                activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.menu?.findItem(R.id.navigation_clubs)?.isChecked = true
            }
            "Events" -> {
                findNavController().navigate(R.id.navigation_events)
                activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.menu?.findItem(R.id.navigation_events)?.isChecked = true
            }
        }
    }


    private fun listOfHPItems(): List<HPItemModel> {
        val hpitemsList = mutableListOf<HPItemModel>()
        hpitemsList.add(
            HPItemModel("Schools", R.drawable.danceschool)
        )

        hpitemsList.add(
            HPItemModel("Clubs", R.drawable.club)
        )

        hpitemsList.add(
            HPItemModel("Events", R.drawable.event)
        )

        hpitemsList.add(
            HPItemModel("Map", R.drawable.map)
        )

        return hpitemsList
    }

}