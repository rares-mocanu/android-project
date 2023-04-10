package com.project.latino.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.project.latino.R
import com.project.latino.adapters.CustomAdapter
import com.project.latino.databinding.FragmentHomeBinding
import com.project.latino.models.HPItemModel

val HPItem = "HPItem"

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
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