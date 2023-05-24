package com.project.latino

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ClipData
import android.content.ClipData.Item
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.core.app.NotificationCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.project.latino.databinding.ActivityMainBinding
import com.project.latino.models.ClubModel
import com.project.latino.models.EventModel
import com.project.latino.models.LocationModel
import com.project.latino.models.SchoolModel
import com.project.latino.ui.fragments.EventFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val notificationClick = intent.getBooleanExtra("NOTIFICATION_CLICK", false)

        if (notificationClick) {
            navController.popBackStack()
            navController.popBackStack()
            navController.navigate(R.id.navigation_events)
        }
        else {
            navController.popBackStack()
            navController.navigate(R.id.navigation_home)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_schools, R.id.navigation_clubs,  R.id.navigation_events,  R.id.navigation_map))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        initializeDatabase()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = "My App Channel"
            val descriptionText = "Channel description"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("MyAppChannel", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }



        // Send notification if current date matches an event date
        CoroutineScope(Dispatchers.Main).launch {
            val instance = AppDatabase.getInstance(this@MainActivity)
            val sortedEvents = ArrayList<EventModel>(instance?.eventDao()!!.getAllEvents())
            Log.d("TAG", "sortedEvents: $sortedEvents")

            // Check if the current date matches any event date
            val currentDate = Calendar.getInstance()
            for (event in sortedEvents) {
                if (currentDate.get(Calendar.YEAR) == event.eventDate.get(Calendar.YEAR) &&
                    currentDate.get(Calendar.MONTH) == event.eventDate.get(Calendar.MONTH) &&
                    currentDate.get(Calendar.DAY_OF_MONTH) == event.eventDate.get(Calendar.DAY_OF_MONTH)
                ) {
                    // Create an Intent to open MainActivity when the notification is clicked
                    val intent = Intent(this@MainActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

                    intent.putExtra("NOTIFICATION_CLICK", true)

                    val pendingIntent = PendingIntent.getActivity(
                        this@MainActivity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)


                    val builder = NotificationCompat.Builder(this@MainActivity, "MyAppChannel")
                        .setSmallIcon(R.drawable.ic_home_black_24dp)
                        .setContentTitle("Don't forget to party!")
                        .setContentText("${event.name} is happening today!")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)

                    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    val notificationId = event.id
                    notificationManager.notify(notificationId, builder.build())
                }
            }

            // Navigate to the Home fragment if the activity was not started by clicking on a notification
            val notificationClick = intent.getBooleanExtra("NOTIFICATION_CLICK", false)
            if (!notificationClick) {
                navController.navigate(R.id.navigation_home)
            }
        }
    }

    private fun initializeDatabase() {
        CoroutineScope(Dispatchers.Main).launch {
            val instance = AppDatabase.getInstance(this@MainActivity)
            if (instance!=null) {
                instance.locationDao().deleteAll()
                instance.eventDao().deleteAll()
                instance.clubDao().deleteAll()
                instance.schoolDao().deleteAll()

                var schools = ArrayList<SchoolModel>()
                var trainers = ArrayList<String>()
                trainers.addAll(listOf("Petru", "Sandra", "Ilias", "Allana"))
                schools.add(SchoolModel(1, "La Puerta", trainers, R.drawable.lapuerta))
                trainers = ArrayList<String>()
                trainers.addAll(listOf("Dana", "Dimitrie", "Marius", "Agata"))
                schools.add(SchoolModel(2, "Conexion", trainers, 0))
                instance.schoolDao().insertSchool(schools[0])
                instance.schoolDao().insertSchool(schools[1])

                var clubsList = ArrayList<ClubModel>()
                clubsList.add(ClubModel(1, "Rio", R.drawable.rio))
                clubsList.add(ClubModel(2, "V Lounge", R.drawable.latinexperience))
                instance.clubDao().insertClub(clubsList[0])
                instance.clubDao().insertClub(clubsList[1])

                var sortedEvents= ArrayList<EventModel>()
                val pictures = ArrayList(listOf(R.drawable.rio, R.drawable.rio, R.drawable.rio))
                var date=Calendar.getInstance()
                var time=Calendar.getInstance()
                time.set(Calendar.HOUR_OF_DAY, 20)
                time.set(Calendar.MINUTE, 0)
                time.set(Calendar.SECOND, 0)
                date.set(2023, Calendar.APRIL, 20)
                sortedEvents.add(
                    EventModel(1,date.clone() as Calendar,time,"Palladium Party","details",pictures,clubsList[0].clubID)
                )
                date.set(2024, Calendar.DECEMBER, 26)
                sortedEvents.add(
                    EventModel(2,date.clone() as Calendar,time,"Kiz Party","details",pictures,clubsList[0].clubID)
                )
                date.set(2024, Calendar.FEBRUARY, 28)
                sortedEvents.add(
                    EventModel(3,date.clone() as Calendar,time,"Latin Experience","details",pictures,clubsList[1].clubID)
                )
                date.set(2023, Calendar.MAY, 24)
                sortedEvents.add(
                    EventModel(4,date.clone() as Calendar,time,"Q-Ban Project","details",pictures,clubsList[1].clubID)
                )
                for(e in sortedEvents) { instance.eventDao().insertEvent(e) }

                var waypoints = ArrayList<LocationModel>()
                waypoints.add(
                    LocationModel(1,"La Puerta",
                    44.450823944678234, 26.100612061664595,
                    "La Puerta Dance Studio"))
                waypoints.add(
                    LocationModel(2,"Conexion",
                    44.43564544135487, 26.094682528572854,
                    "Conexion Dance School"))
                instance.locationDao().insertWaypoint(waypoints[0])
                instance.locationDao().insertWaypoint(waypoints[1])

                }

        }
    }



    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
