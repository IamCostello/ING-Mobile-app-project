package com.example.kotlinpostapi.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.kotlinpostapi.databinding.FragmentMapBinding


import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class Map : SupportMapFragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentMapBinding
    private var googleMap: GoogleMap? = null
    val args: MapArgs by navArgs()
    private lateinit var mapView: MapView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentMapBinding.inflate(inflater, container, false)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        binding.mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        binding.mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onMapReady(gMap: GoogleMap?) {
        googleMap = gMap
        val latLng = LatLng(args.userLat.toDouble(), args.userLng.toDouble())
        val markerOptions: MarkerOptions = MarkerOptions().position(latLng).title("USER LOCATION")
        val zoomLevel = 5.0f
        googleMap.let {
            it!!.addMarker(markerOptions)
            it.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel))
        }

    }


}