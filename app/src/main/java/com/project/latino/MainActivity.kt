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
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.project.latino.databinding.ActivityMainBinding
import com.project.latino.models.EventModel
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


//        // Check if the activity was started by clicking on the notification
//        val notificationClick = intent.getBooleanExtra("NOTIFICATION_CLICK", false)
//
//        if (notificationClick) {
//            // Navigate to the Events fragment
//            //navController.popBackStack()
//            navController.navigate(R.id.navigation_events)
//        } else {
//            // Navigate to the Home fragment
//            //doesnt work
//            navController.navigate(R.id.navigation_home)
//            //doesnt work
//        }

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


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
