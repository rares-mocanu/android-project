package com.project.latino.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.latino.R
import com.project.latino.adapters.SchoolAdapter
import com.project.latino.databinding.ActivitySchoolBinding
import com.project.latino.models.SchoolModel

class SchoolFragment : Fragment() {

    private lateinit var binding: ActivitySchoolBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivitySchoolBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val schoolrecycler = binding.schoolRecyclerView
        val layoutManager = LinearLayoutManager(requireContext())
        schoolrecycler.layoutManager = layoutManager
        var schools = ArrayList<SchoolModel>(100)
        var trainers = ArrayList<String>()
        trainers.addAll(listOf("Petru", "Sandra", "Ilias", "Allana"))
        schools.add(SchoolModel(1, "La Puerta", trainers, R.drawable.lapuerta))
        trainers = ArrayList<String>()
        trainers.addAll(listOf("Dana", "Dimitrie", "Marius", "Agata"))
        schools.add(SchoolModel(1, "Conexion", trainers, 0))
        schoolrecycler.adapter = SchoolAdapter(schools)
    }
}