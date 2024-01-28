package com.example.sportbud3.Activities

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.example.sportbud3.Adapters.PlaceArrayAdapter
import com.example.sportbud3.Model.PlaceDataModel
import com.example.sportbud3.R
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.android.synthetic.main.activity_auto_complete.*
import java.util.*


class AutoCompleteActivity : AppCompatActivity(),OnMapReadyCallback {
    private var mMap: GoogleMap? = null
    private var placeAdapter: PlaceArrayAdapter? = null
    private lateinit var mPlacesClient: PlacesClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.sportbud3.R.layout.activity_auto_complete)


        }
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        Places.initialize(this, "AIzaSyD9VWfICdISNlqIajLlCQK9P0liD73yGqQ")
        mPlacesClient = Places.createClient(this)

        val mapFragment: SupportMapFragment? = supportFragmentManager.findFragmentById(R.id.mapLocation) as? SupportMapFragment
        mapFragment?.getMapAsync(this)

        placeAdapter = PlaceArrayAdapter(this, R.layout.layout_item_places, mPlacesClient)
        autoCompleteEditText.setAdapter(placeAdapter)

        autoCompleteEditText.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
            val place = parent.getItemAtPosition(position) as PlaceDataModel
            autoCompleteEditText.apply {
                setText(place.fullText)
                setSelection(autoCompleteEditText.length())
            }
        }
    }

    override  fun onMapReady(p0: GoogleMap) {

    }



}