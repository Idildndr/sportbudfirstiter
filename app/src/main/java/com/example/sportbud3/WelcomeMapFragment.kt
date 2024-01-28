package com.example.sportbud3

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.sportbud.Model.RoomListItem
import com.example.sportbud3.Adapters.CustomInfoWindowAdapter
import com.example.sportbud3.services.RetrofitInstance
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.fragment_my.*




class WelcomeMapFragment : Fragment() {
    private lateinit var mMap: GoogleMap
    private var mMapView: MapView? = null
    private lateinit var RoomListArray: ArrayList<RoomListItem>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_welcome_map, container, false)

        mMapView = rootView.findViewById(R.id.mapView2)
        mMapView?.onCreate(savedInstanceState)


        mMapView?.getMapAsync(object : OnMapReadyCallback {
            override fun onMapReady(googleMap: GoogleMap) {



                mMap = googleMap



                lifecycleScope.launchWhenCreated {
                    val response = RetrofitInstance.retrofit.getAllRooms()
                    if (response.isSuccessful && response.body() != null) {
                        RoomListArray = response.body()!!.toMutableList() as ArrayList<RoomListItem>
                        RoomListArray.forEach {
                            val temp = LatLng(
                                it.contents?.latitude.toString().toDouble(),
                                it.contents?.longitude.toString().toDouble()
                            )
                            val lat= 38.454991
                            val long =27.145735
                            val zoomLevel = 11f
                            val izmir = LatLng(lat,long)
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(izmir, zoomLevel))

                            googleMap.addMarker(MarkerOptions().position(temp).title(it.title))
                            googleMap.addMarker(MarkerOptions().position(temp).snippet(it.contents?.slogan))
                            //  googleMap.addMarker(MarkerOptions().position(temp).icon())
                        }


                    }
                }

            }
        })

        return rootView
    }

    override fun onResume() {
        super.onResume()
        mMapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView?.onLowMemory()
    }
}

