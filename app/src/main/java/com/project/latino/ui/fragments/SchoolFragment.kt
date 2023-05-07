package com.project.latino.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.latino.AppDatabase
import com.project.latino.R
import com.project.latino.adapters.SchoolAdapter
import com.project.latino.databinding.ActivitySchoolBinding
import com.project.latino.models.SchoolModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        val schoolRecycler = binding.schoolRecyclerView
        val layoutManager = LinearLayoutManager(requireContext())
        schoolRecycler.layoutManager = layoutManager
        schoolRecycler.adapter = SchoolAdapter(ArrayList<SchoolModel>(100))

        CoroutineScope(Dispatchers.Main).launch{
            val instance=AppDatabase.getInstance(requireContext())
            var schools= ArrayList<SchoolModel>(instance?.schoolDao()!!.getAllSchools())
            if(schools.size<2)
                {
                instance.schoolDao().deleteAll()
                    var trainers = ArrayList<String>()
                    trainers.addAll(listOf("Petru", "Sandra", "Ilias", "Allana"))
                    schools.add(SchoolModel(1, "La Puerta", trainers, R.drawable.lapuerta))
                    trainers = ArrayList<String>()
                    trainers.addAll(listOf("Dana", "Dimitrie", "Marius", "Agata"))
                    schools.add(SchoolModel(2, "Conexion", trainers, 0))
                    instance.schoolDao().insertSchool(schools[0])
                    instance.schoolDao().insertSchool(schools[1])
                }
                schoolRecycler.adapter = SchoolAdapter(schools)
        }
        val searchText=binding.schoolSearchText
        val searchbutton = binding.schoolSearchButton
        searchbutton.setOnClickListener(OnClickListener {
            val schoolname=searchText.text.toString()
            CoroutineScope(Dispatchers.Main).launch{
                val instance=AppDatabase.getInstance(requireContext())
                var schools= ArrayList<SchoolModel>(instance?.schoolDao()!!.getSchoolsLikeName(schoolname))
                schoolRecycler.adapter = SchoolAdapter(schools)
            }

        })

    }
}