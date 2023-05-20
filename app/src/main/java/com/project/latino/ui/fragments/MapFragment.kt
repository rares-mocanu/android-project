package com.project.latino.ui.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.project.latino.AppDatabase
import com.project.latino.R
import com.project.latino.databinding.FragmentMapsBinding
import com.project.latino.models.LocationModel
import com.project.latino.models.SchoolModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapsBinding
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val locationRequestCode = 1000
    private lateinit var currentLocation: LatLng

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(inflater, container, false)


        if ((ActivityCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            and (ActivityCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        ) {
            println("permission not granted")
            // Request location permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                locationRequestCode
            )
        }
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        println("map ready")
        this.googleMap = googleMap
        // You can customize the map here
        // Initialize fused location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        println(ActivityCompat.checkSelfPermission(requireContext(),
            android.Manifest.permission.ACCESS_FINE_LOCATION) )

        // Check if the user has granted location permission
        if ((ActivityCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            and (ActivityCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        ){
            currentLocation = LatLng(44.417805, 26.098794)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 13f))
        } else {
            // Permission has already been granted
            // Get the user's current location
            fusedLocationClient.lastLocation.addOnSuccessListener { location :android.location.Location ->
                    currentLocation = LatLng(location.latitude, location.longitude)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 13f))

            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            val instance = AppDatabase.getInstance(requireContext())
            var waypoints = ArrayList<LocationModel>(instance?.locationDao()!!.getAllWaypoints())
            if(waypoints.size !=2){
                waypoints.clear()
                waypoints.add(LocationModel(1,"La Puerta",
                        44.450823944678234, 26.100612061664595,
                    "La Puerta Dance Studio"
                ))
                waypoints.add(LocationModel(2,"Conexion",
                    44.43564544135487, 26.094682528572854,
                    "Conexion Dance School"
                ))
                instance.locationDao().deleteAll()
                instance.locationDao().insertWaypoint(waypoints[0])
                instance.locationDao().insertWaypoint(waypoints[1])
            }
            for(waypoint in waypoints)
            {
                googleMap.addMarker(MarkerOptions()
                    .title(waypoint.name)
                    .position(LatLng(waypoint.lat,waypoint.lng))
                    .snippet(waypoint.desc)
                )
            }
        }

        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
