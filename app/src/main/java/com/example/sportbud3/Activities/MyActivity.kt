package com.example.sportbud3.Activities

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import com.google.android.gms.maps.OnMapReadyCallback
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.sportbud3.R
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.Place.Field
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.android.synthetic.main.fragment_my.*


class MyActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var mapView: MapView
    private lateinit var locationProvider: FusedLocationProviderClient
    private lateinit var intentCreateRoom: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_my)

        // Initialize Places SDK
        Places.initialize(applicationContext, getString(R.string.api_key))


        // Initialize MapView
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        locationProvider = LocationServices.getFusedLocationProviderClient(this)
        // Initialize AutocompleteSupportFragment
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG
            )
        )
        autocompleteFragment.setCountry("TR")


        // Set up PlaceSelectionListener
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                go_back_createroom_button.background.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(this@MyActivity, R.color.mtrl_textinput_default_box_stroke_color),PorterDuff.Mode.MULTIPLY)
                go_back_createroom_button.setTextColor(ContextCompat.getColor(this@MyActivity, R.color.white))
                intentCreateRoom = Intent(this@MyActivity, CreateRoomActivity::class.java)


                val latLng = place.latLng
                if (latLng != null) {
                    val latitude = latLng?.latitude.toString()
                    val longitude = latLng?.longitude.toString()

                    intentCreateRoom.putExtra("lat", latitude)
                    intentCreateRoom.putExtra("long", longitude)
                    googleMap.addMarker(
                        MarkerOptions()
                            .position(latLng)
                            .title(place.name)
                    )
                    intentCreateRoom.putExtra("placeName", place.name)
                    Toast.makeText(this@MyActivity, latLng.toString(), Toast.LENGTH_LONG).show()


                    // googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15f)
                    googleMap.animateCamera(cameraUpdate)

                }
            }


            override fun onError(p0: Status) {
                Toast.makeText(this@MyActivity, "Error: ${p0.statusMessage}", Toast.LENGTH_LONG)
                    .show()
                Log.d("map", "Error: ${p0.statusMessage}")
            }
        })
        go_back_createroom_button.setOnClickListener {
            startActivity(intentCreateRoom)
        }

    }

    override fun onMapReady(p0: GoogleMap) {
        if (p0 != null) {
            googleMap = p0
            googleMap.uiSettings.isZoomControlsEnabled = true
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
