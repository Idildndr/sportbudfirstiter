package com.example.sportbud3

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.sportbud.Model.RoomListItem
import com.example.sportbud3.Adapters.CustomInfoWindowAdapter
import com.example.sportbud3.services.RetrofitInstance

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapsFragment : Fragment() {
    private lateinit var RoomListArray: ArrayList<RoomListItem>
    private val camAndViewport by lazy {
        CamAndViewport()
    }

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))



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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }



}
class CamAndViewport {
    val ksk: CameraPosition = CameraPosition.Builder()
        .target(LatLng(38.53377166240755, 27.130879952822003))
        .zoom(10f)
        .tilt(90f)
        .bearing(0f)
        .build()

    val kskBounds = LatLngBounds(
        LatLng(38.3911374190037, 27.024240059609), //sw
        LatLng(38.48799589677025, 27.209171082243866))    //NE



    val melbourneBounds = LatLngBounds (
        LatLng(-38.45007744433469, 144.2546884913213),
        LatLng(38.415803464253024, 27.263953808373298))
}