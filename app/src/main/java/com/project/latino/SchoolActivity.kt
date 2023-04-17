package com.project.latino

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.latino.adapters.SchoolAdapter
import com.project.latino.databinding.ActivitySchoolBinding
import com.project.latino.models.SchoolModel

class SchoolActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySchoolBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySchoolBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val schoolrecycler =binding.schoolRecyclerView
        val layoutManager = LinearLayoutManager(this)
        schoolrecycler.layoutManager=layoutManager
        var schools = ArrayList<SchoolModel>(100)
        var trainers=ArrayList<String>()
        trainers.addAll(listOf("Petru","Sandra","Ilias","Allana"))
        schools.add( SchoolModel(
            1,"La Puerta", trainers,R.drawable.lapuerta))
        trainers=ArrayList<String>()
        trainers.addAll(listOf("Dana","Dimitrie","Marius","Agata"))
        schools.add(SchoolModel(1,"Conexion",trainers,0))
        schoolrecycler.adapter= SchoolAdapter(schools)



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