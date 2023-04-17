package com.project.latino

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.latino.adapters.ClubsAdapter
import com.project.latino.databinding.ActivityClubsBinding
import com.project.latino.models.ClubModel

class ClubsActivity : AppCompatActivity(){

    private lateinit var binding: ActivityClubsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityClubsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val clubsrecycler =binding.clubsRecyclerView
        val layoutManager = LinearLayoutManager(this)

        clubsrecycler.layoutManager=layoutManager
        val clubslist= ArrayList<ClubModel>(100)
        var clubeventslist =  ArrayList<String>()
        clubeventslist.addAll(listOf("Palladium party","Kiz party"))
        clubslist.add(ClubModel(0,"Rio",clubeventslist,R.drawable.rio))
        clubeventslist =  ArrayList<String>()
        clubeventslist.addAll(listOf("Latin Experience","Q-Ban Project"))
        clubslist.add(ClubModel(1,"V Lounge",clubeventslist,R.drawable.latinexperience))

        clubsrecycler.adapter= ClubsAdapter(clubslist)



        var navschoolbutton : View =findViewById<View>(R.id.navigation_schools)
        navschoolbutton.setOnClickListener{
            val intent = Intent(this,SchoolActivity::class.java)
            startActivity(intent)
        }
        var navhomebutton : View =findViewById<View>(R.id.navigation_home)
        navhomebutton.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        var navclubsbutton : View =findViewById<View>(R.id.navigation_clubs)
        navclubsbutton.setOnClickListener{
            val intent = Intent(this,ClubsActivity::class.java)
            startActivity(intent)
        }

    }


}