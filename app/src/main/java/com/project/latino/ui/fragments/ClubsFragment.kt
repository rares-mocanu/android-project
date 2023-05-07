package com.project.latino.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.latino.AppDatabase
import com.project.latino.R
import com.project.latino.adapters.ClubsAdapter
import com.project.latino.adapters.SchoolAdapter
import com.project.latino.databinding.ActivityClubsBinding
import com.project.latino.models.ClubModel
import com.project.latino.models.SchoolModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        clubsRecyclerView.adapter = ClubsAdapter(ArrayList<ClubModel>(100))

        CoroutineScope(Dispatchers.Main).launch{
            val instance= AppDatabase.getInstance(requireContext())
            var clubsList= ArrayList<ClubModel>(instance?.clubDao()!!.getAllClubs())
            if(clubsList.size<2)
            {
                instance.clubDao().deleteAll()
                clubsList.add(ClubModel(0,"Rio",R.drawable.rio))
                instance.clubDao().insertClub(ClubModel(0,"Rio",R.drawable.rio))
                clubsList.add(ClubModel(1,"V Lounge",R.drawable.latinexperience))
                instance.clubDao().insertClub(ClubModel(1,"V Lounge",R.drawable.latinexperience))
            }
            clubsRecyclerView.adapter = ClubsAdapter(clubsList)
        }


    }
}